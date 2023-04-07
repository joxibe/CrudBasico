package com.crud1.crud.service;

import com.crud1.crud.entity.Producto;
import com.crud1.crud.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository; //spring crea una instancia del repositorio

    public List<Producto> list(){
        return productoRepository.findAll();
    }

    public Optional<Producto> getOne(int id){ //optional, puede ser nulo o mas de uno
        return productoRepository.findById(id);
    }

    public Optional<Producto> getByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public void save ( Producto producto){
        productoRepository.save(producto);
    }

    public void delete(int id){
        productoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return productoRepository.existsById(id); //si existe devuelve true, si no false
    }

    public boolean existsByNombre(String nombre){
        return productoRepository.existsByNombre(nombre); //si existe devuelve true, si no false
    }

}
