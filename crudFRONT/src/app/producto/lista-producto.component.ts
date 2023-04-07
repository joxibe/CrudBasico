import { Component, OnInit } from '@angular/core';
import { Producto } from '../models/producto';
import { ProductoService } from '../service/producto.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista-producto',
  templateUrl: './lista-producto.component.html',
  styleUrls: ['./lista-producto.component.css']
})
export class ListaProductoComponent implements OnInit {

  productos: Producto[] = [];

  constructor(
    private productoService: ProductoService,
    private toastr: ToastrService,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.cargaProductos();
  }

  cargaProductos(): void{
    this.productoService.lista().subscribe(//cuando recibimos los productos nos suscribimos para mostrarlos en pantalla
      data => {
        this.productos = data;//llenamos la lista producto con los datos obtenidos en la consulta del servicio front del backend
      },
      err => {
        console.log(err);
      }
    );
  }

  borrar(id: number){
    this.productoService.delete(id).subscribe(
      data =>{
        this.toastr.success('Producto eliminado', 'Ok',
        {timeOut: 3000, positionClass: 'toast-bottom-center'});
      this.cargaProductos();
      },
      err =>{
        this.toastr.error(err.error.mensaje, 'Fail',
        {timeOut: 3000, positionClass: 'toast-bottom-center'})
      }
    );
  }

}
