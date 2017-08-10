/*
 * #%L
 * %%
 * Copyright (C) 2015 - 2016 Trustsystems Desenvolvimento de Sistemas, LTDA.
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
package br.com.trustsystems.elfinder.support.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Helper class for NIO file system operations.
 *
 * @author Thiago Gutenberg Carvalho da Costa
 */
public final class NioHelper {

    private NioHelper() {
        // supress the default constructor
        // for not instantiability
        throw new AssertionError();
    }

    /**
     * Creates a new and empty file.
     *
     * @param path the path to the file to create.
     * @throws IOException if something goes wrong.
     */
    public static void createFile(Path path) throws IOException {
        Files.createFile(path);
    }

    /**
     * Creates a directory by creating all nonexistent parent directories first.
     *
     * @param path the directory to create
     * @throws IOException if something goes wrong.
     */
    public static void createFolder(Path path) throws IOException {
        Files.createDirectories(path);
    }

    /**
     * Deletes a file if it exists.
     *
     * @param path the path to the file to delete.
     * @throws IOException if something goes wrong.
     */
    public static void deleteFile(Path path) throws IOException {
        if (!isFolder(path)) {
            Files.deleteIfExists(path);
        }
    }

    /**
     * Deletes a directory if it exists.
     *
     * @param path the path to the directory to delete.
     * @throws IOException if something goes wrong.
     */
    public static void deleteFolder(Path path) throws IOException {
        deleteFolder(path, true);
    }

    /**
     * Deletes a directory if it exists.
     *
     * @param path              the path to the directory to delete.
     * @param bypassNotEmptyDir flag that indicates to delete not empty folder.
     * @throws IOException if something goes wrong.
     */
    public static void deleteFolder(Path path, boolean bypassNotEmptyDir) throws IOException {
        if (isFolder(path)) {
            if (bypassNotEmptyDir) {
                List<Path> listChildren = listChildren(path);
                for (Path children : listChildren) {
                    if (isFolder(children)) {
                        deleteFolder(children, bypassNotEmptyDir);
                    } else {
                        deleteFile(children);
                    }
                }
            }
            Files.deleteIfExists(path);
        }
    }

    /**
     * Tests whether a file exists.
     *
     * @param path the path to the file to test.
     * @return true if the file exists; false if the file does not exist or its
     * existence cannot be determined.
     */
    public static boolean exists(Path path) {
        return Files.exists(path);
    }

    /**
     * Returns a file's last modified time in milliseconds.
     *
     * @param path the path to the file.
     * @return the last modified time in milliseconds.
     * @throws IOException if something goes wrong.
     */
    public static long getLastModifiedTimeInMillis(Path path) throws IOException {
        return Files.getLastModifiedTime(path).toMillis();
    }

    /**
     * Gets a file's content type.
     *
     * @param path the path to the file to probe.
     * @return the file content type.
     * @throws IOException if something goes wrong.
     */
    public static String getMimeType(Path path) throws IOException {
        // An implementation using "Java Service Provider Interface (SPI)" is
        // registered in /META-INF/services/java.nio.file.spi.FileTypeDetector,
        // improving the standard default NIO implementation with the Apache Tika API.
        return Files.probeContentType(path);
    }

    /**
     * Returns the name of the file or directory from the given path.
     *
     * @param path the path to the file or directory.
     * @return the name of the file or directory.
     */
    public static String getName(Path path) {
        return path.getFileName().toString();
    }

    /**
     * Returns the parent path, or null if this path does not have a parent.
     *
     * @param path the path to the file or directory.
     * @return the parent path, or null if this path does not have a parent.
     */
    public static Path getParent(Path path) {
        return path.getParent();
    }

    /**
     * Gets the relative path based on a root path and a target path.
     *
     * @param rootPath the supposed base path from the given target path.
     * @param path     the target path.
     * @return the relative path.
     * @throws IOException if something goes wrong.
     */
    public static String getRelativePath(Path rootPath, Path path) throws IOException {
//        return path.relativize(rootPath).toString();
        String relativePath = "";
        // to avoid java.nio.file.NoSuchFileException lets use the absolute path string
        // to check if the given paths are the same.
        String r = rootPath.toString().trim();
        String p = path.toString().trim();
//        if (!isSame(rootPath, path) && path.startsWith(rootPath)) {
        if (!p.equalsIgnoreCase(r) && p.startsWith(r)) {
            relativePath = path.subpath(rootPath.getNameCount(), path.getNameCount()).toString();
        }
//        throw new IllegalArgumentException("The provided paths are same or the first path parameter is not parent of the second path parameter");
        return relativePath;
    }

    /**
     * Gets total size in bytes from the given path.
     *
     * @param path      the path to the file or directory.
     * @param recursive the flag that define if is to make a recursive call to the
     *                  children paths from the given path.
     * @return total size in bytes.
     * @throws IOException if something goes wrong.
     */

    public static long getTotalSizeInBytes(Path path, boolean recursive) throws IOException {
        if (isFolder(path) && recursive) {
            FileTreeSize fileTreeSize = new FileTreeSize();
            Files.walkFileTree(path, fileTreeSize);
            return fileTreeSize.getTotalSize();
        }
        return Files.size(path);
    }

    /**
     * Tests whether a file is a directory.
     *
     * @param path the path to the file to test.
     * @return true if the file is a directory; false if the file does not
     * exist, is not a directory, or it cannot be determined if the file
     * is a directory or not.
     */
    public static boolean isFolder(Path path) {
        return Files.isDirectory(path);
    }

    /**
     * Tests if two paths locate the same file.
     *
     * @param expected expected path.
     * @param actual   actual path.
     * @return true if, and only if, the two paths locate the same file
     * @throws IOException if something goes wrong.
     */
    public static boolean isSame(Path expected, Path actual) throws IOException {
        return Files.isSameFile(expected, actual);
    }

    /**
     * Tests if the given directory has children.
     *
     * @param dir path to the directory to test.
     * @return true if it has children, otherwise false.
     * @throws IOException if something goes wrong.
     */
    public static boolean hasChildFolder(Path dir) throws IOException {
        if (isFolder(dir)) {

            // directory filter
            DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
                @Override
                public boolean accept(Path path) throws IOException {
                    return Files.isDirectory(path);
                }
            };

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir, filter)) {
                return directoryStream.iterator().hasNext();
            }
        }
        return false;
    }

    /**
     * Gets children paths from the give directory.
     *
     * @param dir path to the directory.
     * @return the children paths from the give directory.
     * @throws IOException if something goes wrong.
     */
    public static List<Path> listChildren(Path dir) throws IOException {
        return listChildren(dir, null);
    }

    /**
     * Gets children paths from the give directory (excluding hidden files).
     *
     * @param dir path to the directory.
     * @return the children paths from the give directory.
     * @throws IOException if something goes wrong.
     */
    public static List<Path> listChildrenNotHidden(Path dir) throws IOException {
        // not hidden file filter
        DirectoryStream.Filter<Path> notHiddenFilter = new DirectoryStream.Filter<Path>() {
            public boolean accept(Path path) throws IOException {
                return !Files.isHidden(path);
            }
        };
        return listChildren(dir, notHiddenFilter);
    }

    /**
     * Gets children paths from the give directory appling the given filter.
     *
     * @param dir    path to the directory.
     * @param filter the filter to be applied
     * @return the children paths from the give directory.
     * @throws IOException if something goes wrong.
     */
    public static List<Path> listChildren(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
        if (isFolder(dir)) {
            List<Path> list = new ArrayList<>();
            try (DirectoryStream<Path> directoryStream = (filter != null ? Files.newDirectoryStream(dir, filter) : Files.newDirectoryStream(dir))) {
                for (Path p : directoryStream) {
                    list.add(p);
                }
            }
            return Collections.unmodifiableList(list);
        }
        return Collections.emptyList();
    }

    /**
     * Opens a file, returning an input stream to read from the file.
     *
     * @param path the path to the file to open.
     * @return a new input stream.
     * @throws IOException if something goes wrong.
     */
    public static InputStream openInputStream(Path path) throws IOException {
        return Files.newInputStream(path);
    }

    /**
     * Opens or creates a file, returning an output stream that may be used to write bytes to the file.
     *
     * @param path the path to the file to open or create.
     * @return a new output stream
     * @throws IOException if something goes wrong.
     */
    public static OutputStream openOutputStream(Path path) throws IOException {
        return Files.newOutputStream(path);
    }

    /**
     * Renames the origin path denoted by the new destination path.
     *
     * @param origin      origin path to be renamed.
     * @param destination The new destination path for the named path
     * @throws IOException if something goes wrong.
     */
    public static void rename(Path origin, Path destination) throws IOException {
//        origin.toFile().renameTo(destination.toFile());
        Files.move(origin, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Searches a given path to get the given target.
     *
     * @param path       the path to be search.
     * @param target     the target.
     * @param mode       the match mode constraint (EXACT, ANYWHERE).
     * @param ignoreCase the flag that indicates if is to make a ignore case search.
     * @return a list of the found paths that contains the target string.
     * @throws IOException if something goes wrong.
     */
    public static List<Path> search(Path path, String target, FileTreeSearch.MatchMode mode, boolean ignoreCase) throws IOException {
        if (isFolder(path)) {
            FileTreeSearch fileTreeSearch = new FileTreeSearch(target, mode, ignoreCase);
            Files.walkFileTree(path, fileTreeSearch);

            List<Path> paths = fileTreeSearch.getFoundPaths();
            return Collections.unmodifiableList(paths);
        }
        throw new IllegalArgumentException("The provided path is not a directory");
    }

    /**
     * Searches a given path to get the given target.
     *
     * @param path   the path to be search.
     * @param target the target.
     * @return a list of the found paths that contains the target string.
     * @throws IOException if something goes wrong.
     */
    public static List<Path> search(Path path, String target) throws IOException {
        return search(path, target, NioHelper.FileTreeSearch.MatchMode.ANYWHERE, true);
    }

    /**
     * File Tree Size Visitor Inner Class Implementation.
     */
    private static class FileTreeSize extends SimpleFileVisitor<Path> {

        private long totalSize;

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            totalSize += attrs.size();
            return FileVisitResult.CONTINUE;
        }

        public long getTotalSize() {
            return totalSize;
        }
    }

    /**
     * File Tree Search Visitor Inner Class Implementation.
     */
    private static class FileTreeSearch extends SimpleFileVisitor<Path> {

        enum MatchMode {
            EXACT, ANYWHERE
        }

        private final String query;
        private final MatchMode mode;
        private final boolean ignoreCase;
        private final List<Path> foundPaths;

        public FileTreeSearch(String query, MatchMode mode, boolean ignoreCase) {
            this.query = query;
            this.mode = mode;
            this.ignoreCase = ignoreCase;
            this.foundPaths = new ArrayList<>();
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//            if (exc != null) {
//                System.err.println(String.format("Failed to visit the file. %s", String.valueOf(exc)));
//            }
            // if something goes wrong continue...
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Objects.requireNonNull(dir);
            if (exc == null) {
                search(dir);
            }
//            else {
//                // if something goes wrong continue...
//                System.err.println(String.format("Failed to visit the directory. %s", String.valueOf(exc)));
//            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            super.visitFile(file, attrs);
            search(file);
            return FileVisitResult.CONTINUE;
        }

        private void search(Path path) {
            if (path != null && path.getFileName() != null) {
                String fileName = path.getFileName().toString();
                boolean found;
                switch (mode) {
                    case EXACT:
                        if (ignoreCase) {
                            found = fileName.equalsIgnoreCase(query);
                        } else {
                            found = fileName.equals(query);
                        }
                        break;
                    case ANYWHERE:
                        if (ignoreCase) {
                            found = fileName.toLowerCase().contains(query.toLowerCase());
                        } else {
                            found = fileName.contains(query);
                        }
                        break;
                    default:
                        // NOP - This Should Never Happen
                        throw new AssertionError();
                }
                if (found) {
                    foundPaths.add(path);
                }
            }
        }

        public List<Path> getFoundPaths() {
            return Collections.unmodifiableList(foundPaths);
        }

    }
}
