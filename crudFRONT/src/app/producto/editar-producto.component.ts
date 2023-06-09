import { Component, OnInit } from '@angular/core';
import { Producto } from '../models/producto';
import { ProductoService } from '../service/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-editar-producto',
  templateUrl: './editar-producto.component.html',
  styleUrls: ['./editar-producto.component.css']
})
export class EditarProductoComponent implements OnInit {

  producto: Producto = null

  constructor(
    private productoService: ProductoService,
    private activateRouter: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router

  ) { }

  ngOnInit(): void {
    const id = this.activateRouter.snapshot.params['id']; //accedemos al producto con el id
    this.productoService.detail(id).subscribe(
      data => {
        this.producto = data
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail',
        {timeOut: 3000, positionClass: 'toast-bottom-center'})
        this.router.navigate([''])
      }
    )
  }

  onUpdate(): void{
    const id = this.activateRouter.snapshot.params['id'];
    this.productoService.update(id, this.producto).subscribe(
      data => {
        this.toastr.success('Producto actualizado', 'Ok',
        {timeOut: 3000, positionClass: 'toast-bottom-center'}),
        this.router.navigate([''])

      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail',
        {timeOut: 3000, positionClass: 'toast-bottom-center'})
        this.router.navigate([''])
      }
    )
  }

}
