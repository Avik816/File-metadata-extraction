// Importing the necessary libraries.
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class FileMetadataExtractor {

    public static void main(String[] args) {
        // File path
        Path filePath = Paths.get("test_file.txt");

        try {
            // 1. Basic Attributes
            BasicFileAttributes basicAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            System.out.println("File Size: " + basicAttributes.size() + " bytes");
            System.out.println("Creation Time: " + basicAttributes.creationTime());
            System.out.println("Last Modified Time: " + basicAttributes.lastModifiedTime());
            System.out.println("Last Accessed Time: " + basicAttributes.lastAccessTime());
            System.out.println("Is Directory: " + basicAttributes.isDirectory());
            System.out.println("Is Regular File: " + basicAttributes.isRegularFile());
            System.out.println("Is Symbolic Link: " + basicAttributes.isSymbolicLink());
            System.out.println("Is Other: " + basicAttributes.isOther());

            // 2. Owner Information
            FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(filePath, FileOwnerAttributeView.class);
            System.out.println("File Owner: " + ownerAttributeView.getOwner());

            // 3. Permissions (POSIX systems like Linux and macOS)
            try {
                PosixFileAttributes posixAttributes = Files.readAttributes(filePath, PosixFileAttributes.class);
                System.out.println("Permissions: " + PosixFilePermissions.toString(posixAttributes.permissions()));
                System.out.println("Owner: " + posixAttributes.owner());
                System.out.println("Group: " + posixAttributes.group());
            } catch (UnsupportedOperationException e) {
                System.out.println("POSIX attributes not supported on this file system.");
            }

            // 4. DOS Attributes (Windows only)
            try {
                DosFileAttributes dosAttributes = Files.readAttributes(filePath, DosFileAttributes.class);
                System.out.println("Is Read-Only: " + dosAttributes.isReadOnly());
                System.out.println("Is Hidden: " + dosAttributes.isHidden());
                System.out.println("Is Archive: " + dosAttributes.isArchive());
                System.out.println("Is System File: " + dosAttributes.isSystem());
            } catch (UnsupportedOperationException e) {
                System.out.println("DOS attributes not supported on this file system.");
            }

            // 5. File Type (Symbolic link check)
            if (Files.isSymbolicLink(filePath)) {
                Path targetPath = Files.readSymbolicLink(filePath);
                System.out.println("Symbolic Link Target: " + targetPath);
            }

            // 6. File Attributes like checksum or hash (custom attributes, not native)
            // This would require custom implementation if needed

        } catch (IOException e) {
            System.err.println("Error reading file attributes: " + e.getMessage());
        }
    }
}
