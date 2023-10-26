package utils.ftpUtil;


import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public class FtpClientUtilsTest {

    FakeFtpServer fakeFtpServer;
    FTPManager ftpManager;

    @BeforeClass
    public void createMockServer() {

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);
        fakeFtpServer.start();
        ftpManager = new FTPManager("localhost", fakeFtpServer.getServerControlPort(), "user", "password");
        ftpManager.open();
    }

    @Test
    public void listFiles() {
        Collection<String> files = ftpManager.listFiles("");
        System.out.println("File list : "+files);
    }

    @Test
    public void uploadFile() throws URISyntaxException, IOException {
        File file = new File("/Users/shadabsayeed/IdeaProjects/GenericFrameworkNew/GenericAutomationFramework/generic-core/src/main/java/utils/ftpUtil/baz.txt");
        ftpManager.uploadFileToPath(file, "/data/test.txt");
        listFiles();
        System.out.println("Success");
    }


    @Test
    public void givenRemoteFile_whenDownloading_thenItIsOnTheLocalFilesystem() {
        ftpManager.downloadFile("/data/foobar.txt", "downloaded_buz.txt");
        Assert.assertTrue(new File("downloaded_buz.txt").exists());

    }


    //    @AfterMethod
//    public void teardown() throws IOException {
//        ftpManager.close();
//        fakeFtpServer.stop();
//    }
}
