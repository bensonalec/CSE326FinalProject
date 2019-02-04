#include <QtWidgets>

int main(int argc, char *argv[])
{

    QApplication app(argc, argv);

    QMainWindow mainWin;
    mainWin.setCentralWidget(new QPlainTextEdit);
    mainWin.show();
    return app.exec();
}