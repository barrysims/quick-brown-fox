#ifndef Pin_h
#define Pin_h

/*
* Models a pin
*/
class Pin {

  public:
    Pin(int _number) {
        number = _number;
    }
    virtual bool read();
    virtual void write(bool level);

  protected:
    int number;
};

#endif
