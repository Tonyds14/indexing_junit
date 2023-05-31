package com.svi.indexing;

import java.util.Scanner;

public class ScannerInputReader implements InputReader {
    private final Scanner scanner;

    public ScannerInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
