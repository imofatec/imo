import { z } from 'zod'

export const registerSchema = z
  .object({
    name: z
      .string()
      .min(1, { message: 'Nome é obrigatório' })
      .min(5, { message: 'O seu nome precisa ter no mínimo 5 caracteres' })
      .max(20, { message: 'O seu nome pode ter no máximo 20 caracteres' }),

    email: z
      .string()
      .email({ message: 'Email inválido' })
      .min(1, { message: 'Preencha o email' }),

    password: z
      .string()
      .min(8, { message: 'A senha precisa ter no mínimo 8 caracteres' }) 
      .max(50, { message: 'A senha não pode ter mais do que 50 caracteres' })
      .regex(/^\S+$/, { message: 'A senha não pode conter espaços em branco' }) 
      .regex(/^(?=.*[A-Z])(?=.*\d).+$/, {
        message: 'A senha precisa ter no mínimo uma letra maiúscula e 1 número',
      }), 

    confPassword: z
      .string()
      .min(8, { message: 'Senha deve ter no mínimo 6 caracteres' })
      .min(1, { message: 'Preencha o campo de confirmar senha' }) 
  })
  .refine((data) => data.password === data.confPassword, {
    message: 'As senhas não conferem',
    path: ['confPassword'],
  })
