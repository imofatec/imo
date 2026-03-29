import { z } from 'zod'

export const registerSchema = z
  .object({
    name: z
      .string({ message: 'Nome obrigatório' })
      .min(5, { message: 'Nome deve ter no mínimo 5 caracteres' }),
    email: z.string({ message: 'Email obrigatório' }).email({ message: 'Email inválido' }),
    password: z
      .string({ message: 'Senha obrigatório' })
      .min(6, { message: 'Senha deve ter no mínimo 6 caracteres' })
      .regex(/^\S+$/, { message: 'A senha não pode conter espaços em branco' })
      .regex(/^(?=.*[A-Z])(?=.*\d).+$/, {
        message: 'A senha precisa ter no mínimo uma letra maiúscula e 1 número',
      }),
    confPassword: z.string({ message: 'Confirmação de senha obrigatório' }),
  })
  .refine((data) => data.password === data.confPassword, {
    message: 'As senhas não coincidem',
    path: ['confPassword'],
  })

export type RegisterData = z.infer<typeof registerSchema>