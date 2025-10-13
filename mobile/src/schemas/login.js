import {z} from "zod";

export const loginSchema = z.object({
    email: z.string({message: "Email obrigatório"}).email({message: "Email inválido"}),
    password: z.string({message: "Senha obrigatória"}).min(6, {message: "Senha deve ter no mínimo 6 caracteres"}),
});