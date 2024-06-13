package org.example.menu;

import java.io.IOException;

public abstract class MenuTemplate {
    public void toonMenu() throws IOException {
        toonHeader();
        toonOpties();
    }

    protected abstract void toonHeader() throws IOException;
    protected abstract void toonOpties() throws IOException;
}
