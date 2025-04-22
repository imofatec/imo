import { z } from 'zod'

export const lessonSchema = z.object({
  title: z
    .string()
    .min(10, 'O título da aula precisa ter no mínimo 10 caracteres')
    .max(50, 'O título da aula pode ter no máximo 50 caracteres'),

  description: z
    .string()
    .max(600, 'A descrição da aula pode ter no máximo 600 caracteres')
    .optional()
    .or(z.literal('')),

  youtubeLink: z
    .string()
    .regex(
      /^(https:\/\/)?(www\.)?(youtube\.com\/watch\?v=)?[\w-]{11}(&.*)?$/,
      'Preencha um link do YouTube válido ou o código do vídeo (11 caracteres após watch?v=)',
    ),
})

export const createCourseBaseSchema = z.object({
  name: z
    .string()
    .min(10, 'O nome do curso precisa ter no mínimo 10 caracteres')
    .max(100, 'O nome do curso pode ter no máximo 100 caracteres'),

  category: z.string().min(1, 'Preencha a categoria do curso'),
  level: z.string().min(1, 'Preencha o nível do curso'),

  description: z
    .string()
    .min(10, 'A descrição do curso precisa ter no mínimo 10 caracteres')
    .max(300, 'A descrição do curso pode ter no máximo 300 caracteres'),
})

export const createCourseSchema = createCourseBaseSchema.extend({
  lessons: z
    .array(lessonSchema)
    .min(1, 'Um curso precisa ter no mínimo 1 aula')
    .max(100, 'Um curso pode ter no máximo 100 aulas'),
})
