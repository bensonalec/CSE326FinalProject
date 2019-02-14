#include <QtWidgets>

#include "window.h"

int main(int argc, char *argv[])
{
        QApplication app(argc, argv);

        window *mainWin = new window(&app);

        return app.exec();
}