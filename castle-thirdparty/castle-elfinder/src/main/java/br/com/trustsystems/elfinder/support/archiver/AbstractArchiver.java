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
package br.com.trustsystems.elfinder.support.archiver;

import br.com.trustsystems.elfinder.core.Target;
import br.com.trustsystems.elfinder.core.Volume;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract Archiver defines some archive behaviors and this class has some
 * convenient methods. This class must be extended by concrete archive
 * implementations.
 *
 * @author Thiago Gutenberg Carvalho da Costa
 */
public abstract class AbstractArchiver implements Archiver {

    public static final String DEFAULT_ARCHIVE_NAME = "Archive";

    private AtomicInteger count = new AtomicInteger(1);

    /**
     * Defines how to create a archive inputstream.
     *
     * @param bufferedInputStream the inputstream.
     * @return the archive inputstream.
     * @throws IOException if something goes wrong.
     */
    public abstract ArchiveInputStream createArchiveInputStream(BufferedInputStream bufferedInputStream) throws IOException;

    /**
     * Defines how to create a archive outputstream.
     *
     * @param bufferedOutputStream the outputstream.
     * @return the archive outputstream.
     * @throws IOException if something goes wrong.
     */
    public abstract ArchiveOutputStream createArchiveOutputStream(BufferedOutputStream bufferedOutputStream) throws IOException;

    /**
     * Defines how to create a archive entry.
     *
     * @param targetPath    the target path.
     * @param targetSize    the target size.
     * @param targetContent the target bytes.
     * @return the archive entry.
     */
    public abstract ArchiveEntry createArchiveEntry(String targetPath, long targetSize, byte[] targetContent);

    @Override
    public String getArchiveName() {
        return DEFAULT_ARCHIVE_NAME;
    }

    /**
     * Default compress implementation used by .tar and .tgz
     *
     * @return the compress archive target.
     */
    @Override
    public Target compress(Target... targets) throws IOException {
        Target compressTarget = null;

        OutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        ArchiveOutputStream archiveOutputStream = null;

        try {

            for (Target target : targets) {
                // get target volume
                final Volume targetVolume = target.getVolume();

                // gets the target infos
                final String targetName = targetVolume.getName(target);
                final String targetDir = targetVolume.getParent(target).toString();
                final boolean isTargetFolder = targetVolume.isFolder(target);

                if (compressTarget == null) {
                    // create compress file
                    String compressFileName = (targets.length == 1) ? targetName : getArchiveName();
                    Path compressFile = Paths.get(targetDir, compressFileName + getExtension());

                    // creates a new compress file to not override if already exists
                    // if you do not want this behavior, just comment this line
                    compressFile = createFile(true, compressFile.getParent(), compressFile);

                    compressTarget = targetVolume.fromPath(compressFile.toString());

                    // open streams to write the compress target contents and auto close it
                    outputStream = targetVolume.openOutputStream(compressTarget);
                    bufferedOutputStream = new BufferedOutputStream(outputStream);
                    archiveOutputStream = createArchiveOutputStream(bufferedOutputStream);
                }

                if (isTargetFolder) {
                    // compress target directory
                    compressDirectory(target, archiveOutputStream);
                } else {
                    // compress target file
                    compressFile(target, archiveOutputStream);
                }
            }

        } finally {
            // close streams
            if (archiveOutputStream != null) {
                archiveOutputStream.finish();
                archiveOutputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return compressTarget;
    }

    /**
     * Default decompress implementation used by .tar and .tgz
     *
     * @return the decompress target.
     */
    @Override
    public Target decompress(Target targetCompress) throws IOException {
        Target decompressTarget;

        final Volume volume = targetCompress.getVolume();

        // gets the compress target infos
        final String src = targetCompress.toString();
        final String dest = removeExtension(src);

        // open streams to read the compress target contents and auto close it
        try (ArchiveInputStream archiveInputStream = createArchiveInputStream(
                new BufferedInputStream(
                        volume.openInputStream(targetCompress)))) {

            // creates the decompress target infos
            Path decompressDir = Paths.get(dest);

            // creates a new decompress folder to not override if already exists
            // if you do not want this behavior, just comment this line
            decompressDir = createFile(false, decompressDir.getParent(), decompressDir);

            // creates the decompress target infos
            decompressTarget = volume.fromPath(decompressDir.toString());

            // creates the dest folder if not exists
            volume.createFolder(decompressTarget);

            // get the compress target list entry
            ArchiveEntry entry;
            while ((entry = archiveInputStream.getNextEntry()) != null) {
                if (archiveInputStream.canReadEntryData(entry)) {
                    // get the entry infos
                    final String entryName = entry.getName();
                    final Target target = volume.fromPath(Paths.get(decompressDir.toString(), entryName).toString());
                    final Target parent = volume.getParent(target);

                    // create parent folder if not exists
                    if (parent != null && !volume.exists(parent)) {
                        volume.createFolder(parent);
                    }

                    if (!entry.isDirectory()) {
                        // open streams to write the decompress target contents and auto close it
                        try (OutputStream outputStream = new BufferedOutputStream(
                                volume.openOutputStream(target))) {

                            // get entry content
//                            byte[] content = new byte[(int) entry.getSize()];

                            // read and write content
//                            outputStream.write(content);
                            IOUtils.copy(archiveInputStream, outputStream);
                        }
                    }
                }
            }
        }
        return decompressTarget;
    }

    protected final Path createFile(boolean compressFile, Path parent, Path path) {
        Path archiveFile = path;
        if (Files.exists(archiveFile)) {
            String archiveName = getArchiveName() + count.getAndIncrement();
            if (compressFile) {
                archiveName += getExtension();
            }
            archiveFile = createFile(compressFile, parent, Paths.get(parent.toString(), archiveName));
        }
        return archiveFile;
    }

    /**
     * Removes the extension from the given compress file name.
     *
     * @param compressSourceName the compress file name.
     * @return the compress file name without extension.
     */
    public static String removeExtension(String compressSourceName) {
        if (compressSourceName != null) {
            int index = compressSourceName.lastIndexOf('.');
            if (index > 0) {
                return compressSourceName.substring(0, index);
            }
        }
        return compressSourceName;
    }

    /**
     * Defines how to compress a target file.
     *
     * @param target              the target to write in the outpustream.
     * @param archiveOutputStream the archive outputstream.
     * @throws IOException if something goes wrong.
     */
    protected final void compressFile(Target target, ArchiveOutputStream archiveOutputStream) throws IOException {
        addTargetToArchiveOutputStream(target, archiveOutputStream);
    }

    /**
     * Defines how to compress a target directory.
     *
     * @param target              the compress directory.
     * @param archiveOutputStream the archive outputstream.
     * @throws IOException if something goes wrong.
     */
    protected final void compressDirectory(Target target, ArchiveOutputStream archiveOutputStream) throws IOException {
        Volume targetVolume = target.getVolume();
        Target[] targetChildrens = targetVolume.listChildren(target);

        for (Target targetChildren : targetChildrens) {
            if (targetVolume.isFolder(targetChildren)) {
                // go down the directory tree recursively
                compressDirectory(targetChildren, archiveOutputStream);
            } else {
                compressFile(targetChildren, archiveOutputStream);
            }
        }
    }

    /**
     * Add some target to the archive outputstream.
     *
     * @param target              the target to write in the outputstream.
     * @param archiveOutputStream the archive outputstream.
     * @throws IOException if something goes wrong.
     */
    private void addTargetToArchiveOutputStream(Target target, ArchiveOutputStream archiveOutputStream) throws IOException {
        Volume targetVolume = target.getVolume();

        try (InputStream targetInputStream = targetVolume.openInputStream(target)) {
            // get the target infos
            final long targetSize = targetVolume.getSize(target);
            final byte[] targetContent = new byte[(int) targetSize];
            final String targetPath = targetVolume.getPath(target); // relative path

            // creates the entry and writes in the archive output stream
            ArchiveEntry entry = createArchiveEntry(targetPath, targetSize, targetContent);
            archiveOutputStream.putArchiveEntry(entry);
//            archiveOutputStream.write(targetContent);
            IOUtils.copy(targetInputStream, archiveOutputStream);
            archiveOutputStream.closeArchiveEntry();
        }
    }
}
