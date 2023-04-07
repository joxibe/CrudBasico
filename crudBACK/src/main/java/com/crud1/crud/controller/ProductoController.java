package com.crud1.crud.controller;

import com.crud1.crud.dto.Mensaje;
import com.crud1.crud.dto.ProductoDto;
import com.crud1.crud.entity.Producto;
import com.crud1.crud.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id)) //en caso de que no exista ! devolvemos un mensaje
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        // en caso de que si exista, devolvemos un producto
        Producto producto = productoService.getOne(id).get(); //para convertir a un optional se usa .get()
        return new ResponseEntity(producto, HttpStatus.OK);
    }


    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(!productoService.existsByNombre(nombre)) //en caso de que no exista ! devolvemos un mensaje
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        // en caso de que si exista, devolvemos un producto
        Producto producto = productoService.getByNombre(nombre).get(); //para convertir a un optional se usa .get()
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productDto){//como pasamos un json por http debemos recibirlo
        if(productoService.existsByNombre(productDto.getNombre())) //si el nombre ya existe
            //return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Mensaje>(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(productDto.getNombre())) //si el nombre es nullo
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if( productDto.getPrecio()<0) //si el precio es meno a 0
            return new ResponseEntity(new Mensaje("El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);


        //creamos un producto con los datos del front
        Producto producto = new Producto(productDto.getNombre(), productDto.getPrecio());
        //guardamos
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id,  @RequestBody ProductoDto productDto){//como pasamos un json por http debemos recibirlo
        if(!productoService.existsById(id)) //en caso de que no exista ! devolvemos un mensaje
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        if(productoService.existsByNombre(productDto.getNombre()) && productoService.getByNombre(productDto.getNombre()).get().getId() != id) //si el nombre ya existe
            return new ResponseEntity(new Mensaje("El nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(productDto.getNombre())) //si el nombre es nullo
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productDto.getPrecio()<0) //si el precio es meno a 0
            return new ResponseEntity(new Mensaje("El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);


        Producto producto = productoService.getOne(id).get();
        producto.setNombre(productDto.getNombre());
        producto.setPrecio(productDto.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        if(!productoService.existsById(id)) //en caso de que no exista ! devolvemos un mensaje
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("Producto eliminado"), HttpStatus.OK);
    }
}
