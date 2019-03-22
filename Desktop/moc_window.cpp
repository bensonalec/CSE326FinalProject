/****************************************************************************
** Meta object code from reading C++ file 'window.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.11.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "window.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'window.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.11.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_notif_t {
    QByteArrayData data[1];
    char stringdata0[6];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_notif_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_notif_t qt_meta_stringdata_notif = {
    {
QT_MOC_LITERAL(0, 0, 5) // "notif"

    },
    "notif"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_notif[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       0,    0, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

       0        // eod
};

void notif::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    Q_UNUSED(_o);
    Q_UNUSED(_id);
    Q_UNUSED(_c);
    Q_UNUSED(_a);
}

QT_INIT_METAOBJECT const QMetaObject notif::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_notif.data,
      qt_meta_data_notif,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *notif::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *notif::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_notif.stringdata0))
        return static_cast<void*>(this);
    return QObject::qt_metacast(_clname);
}

int notif::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    return _id;
}
struct qt_meta_stringdata_window_t {
    QByteArrayData data[11];
    char stringdata0[97];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_window_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_window_t qt_meta_stringdata_window = {
    {
QT_MOC_LITERAL(0, 0, 6), // "window"
QT_MOC_LITERAL(1, 7, 16), // "login_successful"
QT_MOC_LITERAL(2, 24, 0), // ""
QT_MOC_LITERAL(3, 25, 13), // "login_failure"
QT_MOC_LITERAL(4, 39, 8), // "closeTab"
QT_MOC_LITERAL(5, 48, 1), // "i"
QT_MOC_LITERAL(6, 50, 9), // "sendNotif"
QT_MOC_LITERAL(7, 60, 9), // "readNotif"
QT_MOC_LITERAL(8, 70, 9), // "reconnect"
QT_MOC_LITERAL(9, 80, 5), // "login"
QT_MOC_LITERAL(10, 86, 10) // "reinitFeed"

    },
    "window\0login_successful\0\0login_failure\0"
    "closeTab\0i\0sendNotif\0readNotif\0reconnect\0"
    "login\0reinitFeed"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_window[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       8,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       2,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    0,   54,    2, 0x06 /* Public */,
       3,    0,   55,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
       4,    1,   56,    2, 0x08 /* Private */,
       6,    0,   59,    2, 0x08 /* Private */,
       7,    0,   60,    2, 0x08 /* Private */,
       8,    0,   61,    2, 0x08 /* Private */,
       9,    0,   62,    2, 0x08 /* Private */,
      10,    0,   63,    2, 0x08 /* Private */,

 // signals: parameters
    QMetaType::Void,
    QMetaType::Void,

 // slots: parameters
    QMetaType::Void, QMetaType::Int,    5,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,

       0        // eod
};

void window::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        window *_t = static_cast<window *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->login_successful(); break;
        case 1: _t->login_failure(); break;
        case 2: _t->closeTab((*reinterpret_cast< int(*)>(_a[1]))); break;
        case 3: _t->sendNotif(); break;
        case 4: _t->readNotif(); break;
        case 5: _t->reconnect(); break;
        case 6: _t->login(); break;
        case 7: _t->reinitFeed(); break;
        default: ;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        {
            using _t = void (window::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&window::login_successful)) {
                *result = 0;
                return;
            }
        }
        {
            using _t = void (window::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&window::login_failure)) {
                *result = 1;
                return;
            }
        }
    }
}

QT_INIT_METAOBJECT const QMetaObject window::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_window.data,
      qt_meta_data_window,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *window::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *window::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_window.stringdata0))
        return static_cast<void*>(this);
    return QObject::qt_metacast(_clname);
}

int window::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 8)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 8;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 8)
            *reinterpret_cast<int*>(_a[0]) = -1;
        _id -= 8;
    }
    return _id;
}

// SIGNAL 0
void window::login_successful()
{
    QMetaObject::activate(this, &staticMetaObject, 0, nullptr);
}

// SIGNAL 1
void window::login_failure()
{
    QMetaObject::activate(this, &staticMetaObject, 1, nullptr);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
