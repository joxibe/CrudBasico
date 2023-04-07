import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  productoURL = 'http://localhost:8080/producto/';

  constructor(private httpCliente: HttpClient) { }

  //metodos del backend
  public lista(): Observable<Producto[]>{
    return this.httpCliente.get<Producto[]>(this.productoURL + 'lista')
  }

  public detail(id: number): Observable<Producto>{
    return this.httpCliente.get<Producto>(this.productoURL + `detail/${id}`) //porque en el backend tiene un parametro  se usan ``
  }

  //no lo usaremos, solo es para ver su implementacion desde el backend
  public detailName(nombre: string): Observable<Producto>{
    return this.httpCliente.get<Producto>(this.productoURL + `detailname/${nombre}`) //porque en el backend tiene un parametro  se usan ``
  }

  public save(producto: Producto): Observable<any> { //any seria igual a ? del backend
    return this.httpCliente.post<any>(this.productoURL + 'create', producto)//como el backend tiene un requesbody enviamos un producto
  }

  public update(id:number, producto: Producto):Observable<any> { //any seria igual a ? del backend
    return this.httpCliente.put<any>(this.productoURL + `update/${id}`, producto)//como el backend tiene un requesbody enviamos un producto
  }

  public delete(id:number): Observable<any>{
    return this.httpCliente.delete<any>(this.productoURL + `delete/${id}`)
  }

}
