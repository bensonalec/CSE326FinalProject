#include <QUdpSocket>






int main(int argc, char **argv){

    QUdpSocket socket = new QUdpSocket();
    socket->bind(QHostAddress::Localhost, 5000);

    connect();

    while(true){
        if(socket->hasPendingDatagrams){
            QNetworkDatagram gram = socket->receiveDatagram();
            //do stuff with data gram maybe start threading here if needed.
            QByteArray data = gram.data();
            std::cout << data.toStdString << endl;

        }

    }





}