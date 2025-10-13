import {z} from "zod";

export const createCourseSchema = z.object({
  nameCourse: z.string({message: "Nome do Curso obrigatório"}).min(15, {message: "O nome deve ter no mínimo 15 caracteres"}),
  category: z.string({message: "O curso deve conter alguma categoria"}),
  level: z.string({message: "O curso deve possuir um nível de dificuldade"}),
  description: z.string({message: "O curso deve conter uma descrição"}).min(25, {message: "Descrição muito curta"})
})