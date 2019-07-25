package generic.problems;

import generic.problems.apitests.FilesAPITests;

public class FilesAPITestApplication {
    private static FilesAPITests fapi = null;

    public static void main (String[] args) {
    	String filename = "C:\\PracticeProjects\\api-tests\\src\\python-code\\errorinstallingubuntu.txt";
    	fapi = new FilesAPITests();
    	System.out.println("Upload file: '" + filename + "'");
    	fapi.uploadFile(filename);
    }
}
