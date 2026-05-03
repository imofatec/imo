import { z } from 'zod'

export const updateUserProfileSchema = z
  .object({
    name: z
      .string()
      .trim()
      .refine((value) => value === '' || value.length >= 4, {
        message: 'Nome deve ter no mínimo 4 caracteres',
      }),
    email: z
      .string()
      .trim()
      .refine((value) => value === '' || z.string().email().safeParse(value).success, {
        message: 'Email inválido',
      }),
    birthDate: z.string().optional(),
    availableTimePerDay: z
      .string()
      .trim()
      .optional(),
    academicDegree: z
      .string()
      .trim()
      .optional(),
    experienceLevel: z
      .string()
      .trim()
      .optional(),
    categoryOfInterest1: z
      .string()
      .trim()
      .optional(),
    categoryOfInterest2: z.string().optional(),
  })
  .refine(
    (data) =>
      !data.categoryOfInterest2 ||
      data.categoryOfInterest1 !== data.categoryOfInterest2,
    {
      message: 'As categorias de interesse não podem ser iguais',
      path: ['categoryOfInterest2'],
    }
  )

export const updateUserBioSchema = z.object({
  bio: z.string().trim().max(300, {
    message: 'A bio deve ter no máximo 300 caracteres',
  }),
})

export const updateUserPasswordSchema = z
  .object({
    oldPassword: z.string({ message: 'Senha atual obrigatória' }).min(1, {
      message: 'Senha atual obrigatória',
    }),
    password: z
      .string({ message: 'Senha obrigatória' })
      .min(8, { message: 'A senha precisa ter no mínimo 8 caracteres' })
      .max(50, { message: 'A senha não pode ter mais do que 50 caracteres' })
      .regex(/^\S+$/, { message: 'A senha não pode conter espaços em branco' })
      .regex(/^(?=.*[A-Z])(?=.*\d).+$/, {
        message: 'A senha precisa ter no mínimo uma letra maiúscula e 1 número',
      }),
    confPassword: z.string({ message: 'Confirmação de senha obrigatória' }),
  })
  .refine((data) => data.password === data.confPassword, {
    message: 'As senhas não coincidem',
    path: ['confPassword'],
  })

export type UpdateUserProfileData = z.infer<typeof updateUserProfileSchema>
export type UpdateUserBioData = z.infer<typeof updateUserBioSchema>
export type UpdateUserPasswordData = z.infer<typeof updateUserPasswordSchema>
