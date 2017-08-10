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
import br.com.trustsystems.elfinder.core.Volume;
import br.com.trustsystems.elfinder.service.ElfinderStorage;
import br.com.trustsystems.elfinder.service.VolumeHandler;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class OpenCommand extends AbstractJsonCommand implements ElfinderCommand {

    @Override
    public void execute(ElfinderStorage elfinderStorage, HttpServletRequest request, JSONObject json)
            throws Exception {

        boolean init = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_INIT) != null;
        boolean tree = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_TREE) != null;
        String target = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_TARGET);

        Map<String, VolumeHandler> files = new LinkedHashMap<>();
        if (init) {
            json.put(ElFinderConstants.ELFINDER_PARAMETER_API, ElFinderConstants.ELFINDER_VERSION_API);
            json.put(ElFinderConstants.ELFINDER_PARAMETER_NETDRIVERS, new Object[0]);
        }

        if (tree) {
            for (Volume volume : elfinderStorage.getVolumes()) {
                VolumeHandler root = new VolumeHandler(volume.getRoot(), elfinderStorage);
                files.put(root.getHash(), root);
                addSubFolders(files, root);
            }
        }

        VolumeHandler cwd = findCwd(elfinderStorage, target);
        files.put(cwd.getHash(), cwd);
        addChildren(files, cwd);

        json.put(ElFinderConstants.ELFINDER_PARAMETER_FILES, buildJsonFilesArray(request, files.values()));
        json.put(ElFinderConstants.ELFINDER_PARAMETER_CWD, getTargetInfo(request, cwd));
        json.put(ElFinderConstants.ELFINDER_PARAMETER_OPTIONS, getOptions(cwd));
    }
}
