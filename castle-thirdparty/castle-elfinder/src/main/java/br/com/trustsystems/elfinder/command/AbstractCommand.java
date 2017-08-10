/*
 * #%L
 * %%
 * Copyright (C) 2015 Trustsystems Desenvolvimento de Sistemas, LTDA.
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Trustsystems Desenvolvimento de Sistemas, LTDA. nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package br.com.trustsystems.elfinder.command;

import br.com.trustsystems.elfinder.ElFinderConstants;
import br.com.trustsystems.elfinder.core.ElfinderContext;
import br.com.trustsystems.elfinder.core.Target;
import br.com.trustsystems.elfinder.service.ElfinderStorage;
import br.com.trustsystems.elfinder.service.VolumeHandler;
import br.com.trustsystems.elfinder.support.archiver.ArchiverOption;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public abstract class AbstractCommand implements ElfinderCommand {

    // visible to subclasses
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String CMD_TMB_TARGET = "?cmd=tmb&target=%s";
    private Map<String, Object> options = new HashMap<>();

    protected void addChildren(Map<String, VolumeHandler> map, VolumeHandler target) throws IOException {
        for (VolumeHandler f : target.listChildren()) {
            map.put(f.getHash(), f);
        }
    }

    protected void addSubFolders(Map<String, VolumeHandler> map, VolumeHandler target) throws IOException {
        for (VolumeHandler f : target.listChildren()) {
            if (f.isFolder()) {
                map.put(f.getHash(), f);
                addSubFolders(map, f);
            }
        }
    }

    protected void createAndCopy(VolumeHandler src, VolumeHandler dst) throws IOException {
        if (src.isFolder()) {
            createAndCopyFolder(src, dst);
        } else {
            createAndCopyFile(src, dst);
        }
    }

    private void createAndCopyFile(VolumeHandler src, VolumeHandler dst) throws IOException {
        dst.createFile();
        InputStream is = src.openInputStream();
        OutputStream os = dst.openOutputStream();
        IOUtils.copy(is, os);
        is.close();
        os.close();
    }

    private void createAndCopyFolder(VolumeHandler src, VolumeHandler dst) throws IOException {
        dst.createFolder();

        for (VolumeHandler c : src.listChildren()) {
            if (c.isFolder()) {
                createAndCopyFolder(c, new VolumeHandler(dst, c.getName()));
            } else {
                createAndCopyFile(c, new VolumeHandler(dst, c.getName()));
            }
        }
    }

    @Override
    public void execute(ElfinderContext context) throws Exception {
        ElfinderStorage elfinderStorage = context.getVolumeSourceFactory().getVolumeSource();
        execute(elfinderStorage, context.getRequest(), context.getResponse());
    }

    public abstract void execute(ElfinderStorage elfinderStorage, HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected Object[] buildJsonFilesArray(HttpServletRequest request, Collection<VolumeHandler> list) throws IOException {
        List<Map<String, Object>> jsonFileList = new ArrayList<>();
        for (VolumeHandler itemHandler : list) {
            jsonFileList.add(getTargetInfo(request, itemHandler));
        }

        return jsonFileList.toArray();
    }

    protected VolumeHandler findCwd(ElfinderStorage elfinderStorage, String target) throws IOException {
        VolumeHandler cwd = null;
        if (target != null) {
            cwd = findTarget(elfinderStorage, target);
        }

        if (cwd == null)
            cwd = new VolumeHandler(elfinderStorage.getVolumes().get(0).getRoot(), elfinderStorage);

        return cwd;
    }

    protected VolumeHandler findTarget(ElfinderStorage elfinderStorage, String hash) throws IOException {
        Target target = elfinderStorage.fromHash(hash);
        if (target == null) {
            return null;
        }
        return new VolumeHandler(target, elfinderStorage);
    }

    protected List<Target> findTargets(ElfinderStorage elfinderStorage, String[] targetHashes) throws IOException {
        if (elfinderStorage != null && targetHashes != null) {
            List<Target> targets = new ArrayList<>(targetHashes.length);
            for (String targetHash : targetHashes) {
                Target target = elfinderStorage.fromHash(targetHash);
                if (target != null)
                    targets.add(target);
            }
            return targets;
        }
        return Collections.emptyList();
    }

    protected Map<String, Object> getTargetInfo(final HttpServletRequest request, final VolumeHandler target) throws IOException {
        Map<String, Object> info = new HashMap<>();

        info.put(ElFinderConstants.ELFINDER_PARAMETER_HASH, target.getHash());
        info.put(ElFinderConstants.ELFINDER_PARAMETER_MIME, target.getMimeType());
        info.put(ElFinderConstants.ELFINDER_PARAMETER_TIMESTAMP, target.getLastModified());
        info.put(ElFinderConstants.ELFINDER_PARAMETER_SIZE, target.getSize());
        info.put(ElFinderConstants.ELFINDER_PARAMETER_READ, target.isReadable() ? ElFinderConstants.ELFINDER_TRUE_RESPONSE : ElFinderConstants.ELFINDER_FALSE_RESPONSE);
        info.put(ElFinderConstants.ELFINDER_PARAMETER_WRITE, target.isWritable() ? ElFinderConstants.ELFINDER_TRUE_RESPONSE : ElFinderConstants.ELFINDER_FALSE_RESPONSE);
        info.put(ElFinderConstants.ELFINDER_PARAMETER_LOCKED, target.isLocked() ? ElFinderConstants.ELFINDER_TRUE_RESPONSE : ElFinderConstants.ELFINDER_FALSE_RESPONSE);

        if (target.getMimeType() != null && target.getMimeType().startsWith("image")) {
            StringBuffer qs = request.getRequestURL();
            info.put(ElFinderConstants.ELFINDER_PARAMETER_THUMBNAIL, qs.append(String.format(CMD_TMB_TARGET, target.getHash())));
        }

        if (target.isRoot()) {
            info.put(ElFinderConstants.ELFINDER_PARAMETER_DIRECTORY_FILE_NAME, target.getVolumeAlias());
            info.put(ElFinderConstants.ELFINDER_PARAMETER_VOLUME_ID, target.getVolumeId());
        } else {
            info.put(ElFinderConstants.ELFINDER_PARAMETER_DIRECTORY_FILE_NAME, target.getName());
            info.put(ElFinderConstants.ELFINDER_PARAMETER_PARENTHASH, target.getParent().getHash());
        }

        if (target.isFolder()) {
            info.put(ElFinderConstants.ELFINDER_PARAMETER_HAS_DIR, target.hasChildFolder() ? ElFinderConstants.ELFINDER_TRUE_RESPONSE : ElFinderConstants.ELFINDER_FALSE_RESPONSE);
        }
        return info;
    }

    protected Map<String, Object> getOptions(VolumeHandler cwd) {
        String[] emptyArray = {};
        options.put(ElFinderConstants.ELFINDER_PARAMETER_PATH, cwd.getName());
        options.put(ElFinderConstants.ELFINDER_PARAMETER_COMMAND_DISABLED, emptyArray);
        options.put(ElFinderConstants.ELFINDER_PARAMETER_FILE_SEPARATOR, ElFinderConstants.ELFINDER_PARAMETER_FILE_SEPARATOR);
        options.put(ElFinderConstants.ELFINDER_PARAMETER_OVERWRITE_FILE, ElFinderConstants.ELFINDER_TRUE_RESPONSE);
        options.put(ElFinderConstants.ELFINDER_PARAMETER_ARCHIVERS, ArchiverOption.JSON_INSTANCE);
        return options;
    }
}