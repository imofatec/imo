// schemas/resetPasswordSchema.js
import { z } from 'zod'

export const resetPasswordSchema = z
  .object({
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
      .min(1, { message: 'Confirme sua senha' }),
    userid: z.string().optional(),
    verificationcode: z.string().optional(),
  })
  .refine((data) => data.password === data.confPassword, {
    message: 'As senhas não conferem',
    path: ['confPassword'],
  })