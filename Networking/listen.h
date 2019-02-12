// myudp.h

#ifndef LISTEN_H
#define LISTEN_H

#include <QObject>
#include <QUdpSocket>

class Listen : public QObject
{
    Q_OBJECT
public:
   // explicit Listen(QObject *parent = 0);
    void start_socket();

signals:
    
public slots:
    void listening();

private:
    QUdpSocket *socket;
    
};

#endif // MYUDP_H