import {z} from "zod";

export const commentSchema = z.object({
    content: z.string({message: "Conteúdo do comentário é obrigatório"}).min(1, {message: "Conteúdo do comentário não pode ser vazio"}).max(300, {message: "Conteúdo do comentário deve ter no máximo 300 caracteres"}),
});