#include <QUdpSocket>






int main(argv[], argc){

    QUdpSocket socket = new QUdpSocket();
    socket->bind(QHostAddress::Localhost, 5000);

    connect(socket, SIGNAL(readyRead()), this),

    while(true){
        if(socket->hasPendingDatagrams){
            QNetworkDatagram gram = socket->receiveDatagram();
            //do stuff with data gram maybe start threading here if needed.
        }

    }





}