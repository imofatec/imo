import { z } from 'zod'

export const commentSchema = z.object({
  content: z
    .string({ message: 'O comentário é obrigatório' })
    .trim()
    .min(1, { message: 'O comentário é obrigatório' })
    .max(200, { message: 'O comentário deve ter no máximo 200 caracteres' }),
  parentId: z.string().trim().optional(),
})

export type CommentData = z.infer<typeof commentSchema>
