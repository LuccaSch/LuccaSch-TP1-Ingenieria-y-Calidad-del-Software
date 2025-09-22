package com.ds.tp.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import com.ds.tp.models.dto.BedelDTO;
import com.ds.tp.models.dto.FiltroBuscarBedelDTO;
import com.ds.tp.models.usuario.Administrador;
import com.ds.tp.models.usuario.Bedel;
import com.ds.tp.repositories.AdminRepository;
import com.ds.tp.repositories.BedelRepository;
import com.ds.tp.util.DSUtilResponseEntity;

@Service
public class BedelService {
    
    // Atributos inyectados por Spring (El passwordEncoder de seguridad, se puede redefinir en passwordEncoder() dentro de config)
    @Autowired
    private final BedelRepository bedelRepository;

    @Autowired
    private final EmpresaService empresaService;

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder; 

    // Constructor
    public BedelService(BedelRepository bedelRepository, EmpresaService empresaService, PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.bedelRepository = bedelRepository;
        this.empresaService = empresaService;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    // FUNCIONES DEL SERVICIO BEDEL

    //--------------------------------------------------POST BEDEL--------------------------------------------------

    public ResponseEntity<Object> postBedel(BedelDTO unBedelDTO) {
        Optional<String> resultadoValidacion = verificarDatos(unBedelDTO);

        // Verificación de datos del bedel
        if (resultadoValidacion.isPresent()) {
            return DSUtilResponseEntity.statusBadRequest(resultadoValidacion.get());
        }
        
        // Verificar el formato de la contraseña
        if (!validarFormatoContrasenia(unBedelDTO.getContrasenia())) {
            return DSUtilResponseEntity.statusConflict("ERROR: Formato de contraseña invalido");
        }

        // Verificar si el usuario ya existe, tanto como bedel o como admin.

        Optional<Bedel> resultadoBedel = bedelRepository.findByUsuario(unBedelDTO.getUsuario());
        
        if (resultadoBedel.isPresent()) {
            String mensajeRetorno="Usuario ya está registrado: " + unBedelDTO.getUsuario();

            return DSUtilResponseEntity.statusConflict(mensajeRetorno);
        }

        Optional<Administrador> resultadoAdmin = adminRepository.findByUsuario(unBedelDTO.getUsuario());
        
        if (resultadoAdmin.isPresent()) {
            String mensajeRetorno="Usuario ya está registrado: " + unBedelDTO.getUsuario();

            return DSUtilResponseEntity.statusConflict(mensajeRetorno);
        }
        
        // Crear el objeto Bedel con la contraseña encriptada
        Bedel unBedel = crearBedel(unBedelDTO);

        // Guardar el bedel en la base de datos
        try {
            bedelRepository.save(unBedel);

            System.out.println("[INFO] Se registró: " + unBedel.toString());

            return DSUtilResponseEntity.statusCreated("El bedel se registró correctamente");

        } catch (DataAccessException e) {
            System.out.println("Error de acceso a datos: " + e.getMessage());

            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde");
        }
        catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());

            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }

    public Optional<String> verificarDatos(BedelDTO unBedel) {
        Optional<String> resultadoVerificado = Optional.empty();
        
        // Validaciones de campos
        if (unBedel.getUsuario().isEmpty() || unBedel.getNombre().isEmpty() || unBedel.getApellido().isEmpty() 
            || unBedel.getContrasenia().isEmpty() || unBedel.getConfContrasenia().isEmpty() || unBedel.getTurno() == 0) {
            resultadoVerificado = Optional.of("ERROR: Se quiere registrar un campo requerido vacío");
        } else if (!unBedel.getContrasenia().equals(unBedel.getConfContrasenia())) {
            resultadoVerificado = Optional.of("ERROR: Contraseña y confirmación de contraseña no son idénticas");
        }
        
        if (unBedel.getUsuario().length() > 30 || unBedel.getUsuario().length() < 5 || unBedel.getNombre().length() > 40 
            || unBedel.getApellido().length() > 40 || unBedel.getContrasenia().length() > 40 || unBedel.getConfContrasenia().length() > 40) {
            resultadoVerificado = Optional.of("ERROR: Campo con cantidad de dígitos incorrecta");
        }
        
        return resultadoVerificado;
    }

    public Bedel crearBedel(BedelDTO unBedelDTO) {
        // Encriptar la contraseña antes de crear el Bedel
        String contraseniaEncriptada = passwordEncoder.encode(unBedelDTO.getContrasenia());
        
        return new Bedel(unBedelDTO.getUsuario(), unBedelDTO.getNombre(), unBedelDTO.getApellido(), contraseniaEncriptada, unBedelDTO.getTurno(), true);
    }

    public boolean validarFormatoContrasenia(String contrasenia) {
        return empresaService.validarRequerimientoContrasenia(contrasenia);
    }



    //--------------------------------------------------GET BEDELS--------------------------------------------------

    public ResponseEntity<Object> getBedels(FiltroBuscarBedelDTO filtroDatos) {
        try{
            if(verificarDatosIncorrectosFiltro(filtroDatos)){
                return DSUtilResponseEntity.statusBadRequest("ERROR: Se quiere filtrar por datos faltantes o incorrectos");
            }


            List<Bedel> bedelList = this.bedelRepository.findAll();

            //Bloque para busqueda por similitud de campos

            if(!filtroDatos.getFiltro().equals("0")){
                // Expresiones regulares para búsquedas por similitud
                String valor = filtroDatos.getValorBusqueda();
                Pattern pattern = Pattern.compile(valor, Pattern.CASE_INSENSITIVE);

                switch (filtroDatos.getFiltro()) {
                    case "1" -> bedelList = bedelList.stream()
                                        .filter(bedel -> pattern.matcher(bedel.getUsuario()).find())
                                        .sorted(Comparator.comparingLong(Bedel::getId))
                                        .collect(Collectors.toList());
                    case "2" -> bedelList = bedelList.stream()
                                        .filter(bedel -> pattern.matcher(bedel.getNombre()).find())
                                        .sorted(Comparator.comparingLong(Bedel::getId))
                                        .collect(Collectors.toList());
                    case "3" -> bedelList = bedelList.stream()
                                        .filter(bedel -> pattern.matcher(bedel.getApellido()).find())
                                        .sorted(Comparator.comparingLong(Bedel::getId))
                                        .collect(Collectors.toList());
                    case "4" -> bedelList = bedelList.stream()
                                        .filter(bedel -> bedel.getTurnoString().equals(valor))
                                        .sorted(Comparator.comparingLong(Bedel::getId))
                                        .collect(Collectors.toList());                                     
                }
            }
            else{
                bedelList = bedelList.stream()
                                .sorted(Comparator.comparingLong(Bedel::getId))
                                .collect(Collectors.toList()); 
            }
            
            List<BedelDTO> bedelListDTO= this.crearListaBedelDto(bedelList);

            return ResponseEntity.status(HttpStatus.OK).body(bedelListDTO);
        }
        catch (DataAccessException e) {
            System.out.println("Error de acceso a datos: " + e.getMessage());
            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde");
        } 
        catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }

    public boolean verificarDatosIncorrectosFiltro(FiltroBuscarBedelDTO filtroDatos){
        if(filtroDatos.getFiltro()==null){
            return true;
        }

        return !filtroDatos.getFiltro().equals("0") && filtroDatos.getValorBusqueda()==null;
    }

    public BedelDTO crearBedelDTO(Bedel unBedel){
        return new BedelDTO(unBedel.getId()
                        ,unBedel.getUsuario()
                        ,unBedel.getNombre()
                        ,unBedel.getApellido()
                        ,unBedel.getTurno()
                        ,unBedel.isEstado());
    }

    public List<BedelDTO> crearListaBedelDto(List<Bedel> listaBedels){
        return listaBedels.stream()
                        .map(bedel -> crearBedelDTO(bedel))
                        .collect(Collectors.toList());
    }



    //--------------------------------------------------UPDATE BEDEL--------------------------------------------------

    public ResponseEntity<Object> putBedel(BedelDTO bedelDTO){
        try{
            if(this.verificarDatosIncorrectosUpdate(bedelDTO)){
                return DSUtilResponseEntity.statusBadRequest("ERROR: No se pudo buscar el bedel debido a la falta de ID en la solicitud");
            }


            Optional<Bedel> bedelOptional= bedelRepository.findById(bedelDTO.getId());

            if(bedelOptional.isEmpty()){
                return DSUtilResponseEntity.statusBadRequest("El bedel que quiere modificar no existe");
            }

            Bedel unBedel= bedelOptional.get();


            if(this.actualizarBedel(unBedel, bedelDTO)){

                this.bedelRepository.save(unBedel);

                BedelDTO bedelModificadoDTO = crearBedelDTO(unBedel);

                String mensaje= "Bedel modificado correctamente"+bedelModificadoDTO.getUsuario();

                return DSUtilResponseEntity.statusOk(mensaje,bedelModificadoDTO);

            }
            else{
                return DSUtilResponseEntity.statusBadRequest("No se modifico el bedel debido a que no se seleccionaron campos a modificar");
            }
        }
        catch (DataAccessException e) {
            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde");
        }
        catch (IllegalArgumentException e){
            return DSUtilResponseEntity.statusBadRequest(e.getMessage());
        }
        catch (Exception e) {
            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }

    public boolean verificarDatosIncorrectosUpdate(BedelDTO bedelDTO){
        return bedelDTO.getId()==null;
    }

    public boolean actualizarBedel(Bedel bedel,BedelDTO bedelDTO) throws IllegalArgumentException{

        boolean actualizado=false;

        if(!(bedelDTO.getNombre()==null)){
            if(bedelDTO.getNombre().length()>40){
                throw new IllegalArgumentException("Error: El nombre no puede tener mas de 40 caracteres.");
            }
            bedel.setNombre(bedelDTO.getNombre());
            actualizado=true;
        }

        if(!(bedelDTO.getApellido()==null)){
            if(bedelDTO.getApellido().length()>40){
                throw new IllegalArgumentException("Error: El apellido no puede tener mas de 40 caracteres.");
            }
            bedel.setApellido(bedelDTO.getApellido());
            actualizado=true;
        }

        if(!(bedelDTO.getTurno()==null)){
            if(!(bedelDTO.getTurno().equals(1)) && !(bedelDTO.getTurno().equals(2))){
                throw new IllegalArgumentException("Error: Solo existe el Turno 1 y 2, debe seleccionar el correcto");
            }
            bedel.setTurno(bedelDTO.getTurno());
            actualizado=true;
        }

        if(!(bedelDTO.getContrasenia()==null)){
            if(!(bedelDTO.getContrasenia().equals(bedelDTO.getConfContrasenia()))){
                throw new IllegalArgumentException("Error: Las contraseñas deben ser iguales");
            }
            if(!(validarFormatoContrasenia(bedelDTO.getContrasenia()))){
                throw new IllegalArgumentException("Error: La contraseña solicitada no cumple las condiciones de la empresa");
            }
            bedel.setContrasenia(passwordEncoder.encode(bedelDTO.getContrasenia()));
            actualizado=true;
        }

        return actualizado;
    }



    //--------------------------------------------------DELETE BEDEL--------------------------------------------------

    public ResponseEntity<Object> deleteBedel(Long id){
        try{
            if(id==null){
                return DSUtilResponseEntity.statusBadRequest("ERROR: No se pudo eliminar el bedel debido a la falta de ID en la solicitud");
            }

            Optional<Bedel> bedelOptional = bedelRepository.findById(id);

            if(bedelOptional.isEmpty()){
                return DSUtilResponseEntity.statusBadRequest("Error al intentar eliminar bedel, no existe dentro de la base de datos");
            }

            Bedel bedel = bedelOptional.get();

            if(!bedel.isEstado()){
                return DSUtilResponseEntity.statusBadRequest("Error se quiere eliminar un bedel que ya se encuentra inactivo");
            }

            bedel.setEstado(false);

            bedelRepository.save(bedel);

            return DSUtilResponseEntity.statusOk("Bedel Eliminado correctamente");
        }
        catch (DataAccessException e) {
            System.out.println("Error de acceso a datos: " + e.getMessage());

            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde");
        }
        catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());

            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }

    public ResponseEntity<Object> activateBedel(Long id){
        try{

            if(id==null){
                return DSUtilResponseEntity.statusBadRequest("ERROR: No se pudo eliminar el bedel debido a la falta de ID en la solicitud");
            }

            Optional<Bedel> bedelOptional = bedelRepository.findById(id);

            if(bedelOptional.isEmpty()){
                return DSUtilResponseEntity.statusBadRequest("Error al intentar activar el bedel, no existe dentro de la base de datos");
            }

            Bedel bedel = bedelOptional.get();

            if(bedel.isEstado()){
                return DSUtilResponseEntity.statusBadRequest("Error se quiere activar un bedel que ya se encuentra inactivo");
            }

            bedel.setEstado(true);

            bedelRepository.save(bedel);

            return DSUtilResponseEntity.statusOk("Bedel Activado correctamente");
        }
        catch (DataAccessException e) {
            System.out.println("Error de acceso a datos: " + e.getMessage());

            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde");
        }
        catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());

            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }
}
