import { z } from 'zod'

export const lessonSchema = z.object({
  nameLesson: z
    .string({ message: 'Nome da aula obrigatório' })
    .trim()
    .min(15, { message: 'O nome deve ter no mínimo 15 caracteres' }),
  link: z
    .string({ message: 'A aula deve conter um link válido' })
    .min(11, { message: 'Link muito curto' }),
  descriptionL: z
    .string({ message: 'A aula deve conter uma descrição' })
    .trim()
    .min(25, { message: 'Descrição muito curta' })
    .max(500, { message: 'A descrição da aula deve ter no máximo 500 caracteres' }),
})

export const createCourseSchema = z.object({
  nameCourse: z
    .string({ message: 'Nome do curso obrigatório' })
    .trim()
    .min(15, { message: 'O nome deve ter no mínimo 15 caracteres' }),
  category: z
    .string({ message: 'O curso deve conter alguma categoria' })
    .trim()
    .min(1, { message: 'O curso deve conter alguma categoria' }),
  level: z
    .string({ message: 'O curso deve possuir um nível de dificuldade' })
    .trim()
    .min(1, { message: 'O curso deve possuir um nível de dificuldade' }),
  description: z
    .string({ message: 'O curso deve conter uma descrição' })
    .trim()
    .min(25, { message: 'Descrição muito curta' })
    .max(300, { message: 'A descrição do curso deve ter no máximo 300 caracteres' }),
  skillIds: z
    .array(z.string().trim().min(1))
    .min(1, { message: 'Selecione pelo menos uma skill' })
    .max(2, { message: 'O curso pode ter no máximo 2 skills' }),
  lessons: z.array(lessonSchema).min(1, { message: 'O curso precisa de pelo menos uma aula' }),
})

export type CreateCourseData = z.infer<typeof createCourseSchema>
