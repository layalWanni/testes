package com.haroldo.minhasfinancas.service.impl;

import com.haroldo.minhasfinancas.exception.RegraNegocioException;
import com.haroldo.minhasfinancas.model.entity.Usuario;
import com.haroldo.minhasfinancas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UsuarioServiceImplTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioServiceImpl usuarioService;


    @Test
    void usuarioExisteTeste(){

        Usuario usuario =  Usuario.builder()
                .email("nissolelo03@gmail.com")
                .senha("postgres")
                .nome("Layal")
                .build();

        Mockito.when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        Assertions.assertThrows(RegraNegocioException.class, ()-> this.usuarioService.salvarUsuario(usuario));

    }

    @Test
    void salvarUsuarioTeste(){
        Usuario usuario =  Usuario.builder()
                .email("nissolelo03@gmail.com")
                .senha("postgres")
                .nome("Layal")
                .build();

        Mockito.when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);

        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario usuario1 = this.usuarioService.salvarUsuario(usuario);

        org.assertj.core.api.Assertions.assertThat(usuario1.getEmail()).isEqualTo("nissolelo03@gmail.com");
        org.assertj.core.api.Assertions.assertThat(usuario1.getNome()).isEqualTo("Layal");
        org.assertj.core.api.Assertions.assertThat(usuario1.getSenha()).isEqualTo("postgres");
    }

    @Test
    void naoSalvarUsuarioComEmailCadastradoTeste(){

        Usuario usuario =  Usuario.builder()
                .email("nissolelo03@gmail.com")
                .senha("postgres")
                .nome("Layal")
                .build();

        Mockito.when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);

        Assertions.assertThrows(RegraNegocioException.class, ()-> this.usuarioService.salvarUsuario(usuario));

    }

    @Test
    void validarEmailTeste(){

        Usuario usuario =  Usuario.builder()
                .email("nissolelo03@gmail.com")
                .senha("postgres")
                .nome("Layal")
                .build();

        Mockito.when(usuarioRepository.findByEmail("nissolelo03@gmail.com")).thenReturn(Optional.of(usuario));
        Usuario usuario1 = this.usuarioService.autenticar("nissolelo03@gmail.com","postgres");

        org.assertj.core.api.Assertions.assertThat(usuario1.getEmail()).isEqualTo("nissolelo03@gmail.com");
    }


    @Test
    void senhaTest(){

        Usuario usuario =  Usuario.builder()
                .email("nissolelo03@gmail.com")
                .senha("postgres")
                .nome("Layal")
                .build();

        Mockito.when(usuarioRepository.findByEmail("nissolelo03@gmail.com")).thenReturn(Optional.of(usuario));
        Usuario usuario1 = this.usuarioService.autenticar("nissolelo03@gmail.com","postgres");

        org.assertj.core.api.Assertions.assertThat(usuario1.getSenha()).isEqualTo("postgres");
    }

}