import { z } from 'zod'

export const loginSchema = z.object({
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
      message: 'A senha precisa ter no mínimo uma letra maiúscula e 1 número'
    })
})

export type LoginData = z.infer<typeof loginSchema>
