import { z } from 'zod'

//ZOD SCHEMAS
const emailSchema = z.string().email({ message: 'Insira um email válido.' })
const urlSchema = z.string().url({ message: 'Insira uma URL válida.' })
const passwordSchema = z
  .string()
  .min(8, {
    message:
      ' A senha deve ter pelo menos 8 caracteres, no maximo 50 caracteres, uma letra maiuscula e um numero.',
  })
  .max(50, { message: '' })
  .refine((value) => /[A-Z]/.test(value), { message: '' })
  .refine((value) => /[0-9]/.test(value), { message: '' })

//VALIDATORS
export const validateEmail = (email) => {
  try {
    emailSchema.parse(email)
    return null
  } catch (e) {
    return e.errors[0].message
  }
}

export const validateUrl = (url) => {
  try {
    urlSchema.parse(url)
    return null
  } catch (e) {
    return e.errors[0].message
  }
}

export const validatePassword = (password) => {
  try {
    passwordSchema.parse(password)
    return null
  } catch (e) {
    return e.errors[0].message
  }
}

