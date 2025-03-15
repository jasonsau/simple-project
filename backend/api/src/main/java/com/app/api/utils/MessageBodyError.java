package com.app.api.utils;

import java.io.Serializable;

public class MessageBodyError  implements Serializable{
    private static final long serialVersionUID = 985885270535689192L;
    String codigo;
    Object mensaje;

    public MessageBodyError(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
    
    public MessageBodyError(String codigo, Object mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Object getMensaje() {
        return mensaje;
    }

    public void setMensaje(Object mensaje) {
        this.mensaje = mensaje;
    }
}
