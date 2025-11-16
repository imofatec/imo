import {z} from "zod";

const lessonSchema = z.object({
  nameLesson: z.string({ message: "Nome da Aula é obrigatório" }).min(15, { message: "O nome deve ter no mínimo 15 caracteres" }),
  link: z.string({ message: "A aula deve conter um link válido" }).min(11, { message: "Link muito curto" }),
  descriptionL: z.string({ message: "A aula deve conter uma descrição" }).min(25, { message: "Descrição muito curta" }),
});

export const createCourseSchema = z.object({
  nameCourse: z.string({ message: "Nome do Curso obrigatório" }).min(15, { message: "O nome deve ter no mínimo 15 caracteres" }),
  category: z.string({ message: "O curso deve conter alguma categoria" }),
  level: z.string({ message: "O curso deve possuir um nível de dificuldade" }),
  description: z.string({ message: "O curso deve conter uma descrição" }).min(25, { message: "Descrição muito curta" }),
  lessons: z.array(lessonSchema).min(1, { message: "O curso precisa de pelo menos uma aula" }),
});
