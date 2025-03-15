export interface MessageError{
    status: string,
    body: {
        mensaje: string | {[key: string]: string},
        codigo: string
    }
}
