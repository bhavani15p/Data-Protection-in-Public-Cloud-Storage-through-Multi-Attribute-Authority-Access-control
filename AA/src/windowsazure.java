/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.microsoft.windowsazure.services.core.storage.*;
import com.microsoft.windowsazure.services.blob.client.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class windowsazure {

    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;"
           + "AccountName=forprojects;" +
             "AccountKey=80d+c+xWZaftzCDkjXefSthQO4kbtD3KHLTfGvK2yGb2eAMmAJeKoT4LxZmX4zRQM04+kgLTWdlffh96JG/cNw==";

    static void downloadFromBlob(String Filename) {

        try {
            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");
            CloudBlob blob = container.getBlockBlobReference(Filename);
            blob.download(new FileOutputStream(common.getinstance().filepath+Filename));
            
            System.out.println("Start Azure Cloud Storage");

            

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void deleteFromBlob(String filename) {
        try {

            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");

            // Retrieve reference to a blob named "myimage.jpg"
            CloudBlockBlob blob = container.getBlockBlobReference(filename);

            // Delete the blob
            blob.delete();

            System.out.println("Deleted from blob");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static void uploadFile(String filename) {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();


            // Get a reference to a container
            // The container name must be lower case
            CloudBlobContainer container = blobClient.getContainerReference("mycontainer");

            // Create the container if it does not exist
            container.createIfNotExist();


            // Create a permissions object
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Include public access in the permissions object
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

            // Set the permissions on the container
            container.uploadPermissions(containerPermissions);


            CloudBlockBlob blob = container.getBlockBlobReference(filename);
            File source = new File(common.getinstance().filepath+filename);
            blob.upload(new FileInputStream(source), source.length());

            System.out.println("Uploading success to windows azure cloud");
            
            File file  = new File(common.getinstance().filepath + filename);
            file.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] arg) {

        try {





        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
