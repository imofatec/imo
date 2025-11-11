import {z} from "zod";

const editLessonSchema = z.object({
  nameLesson: z.string({ message: "Nome da Aula é obrigatório" }).min(15, { message: "O nome deve ter no mínimo 15 caracteres" }).optional(),
  link: z.string({ message: "A aula deve conter um link válido" }).min(11, { message: "Link muito curto" }).optional(),
  descriptionL: z.string({ message: "A aula deve conter uma descrição" }).min(25, { message: "Descrição muito curta" }).optional(),
});

export const editCourseSchema = z.object({
  nameCourse: z.string({ message: "Nome do Curso obrigatório" }).min(15, { message: "O nome deve ter no mínimo 15 caracteres" }).optional(),
  category: z.string({ message: "O curso deve conter alguma categoria" }).optional(),
  level: z.string({ message: "O curso deve possuir um nível de dificuldade" }).optional(),
  description: z.string({ message: "O curso deve conter uma descrição" }).min(25, { message: "Descrição muito curta" }).optional(),
  lessons: z.array(editLessonSchema).min(1, { message: "O curso precisa de pelo menos uma aula" }).optional(),
});
