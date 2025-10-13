import { z } from "zod";

export const registerSchema = z.object({
    user: z
        .string({ message: "Nome obrigatório" })
        .min(5, { message: "Nome deve ter no mínimo 5 caracteres" }),
    email: z
        .string({ message: "Email obrigatório" }).email({ message: "Email inválido" }),
    password: z
        .string({ message: "Senha obrigatório" })
        .min(6, { message: "Senha deve ter no mínimo 6 caracteres" }),
    confirmPassword: z
        .string({ message: "Confirmação de senha obrigatório" }),
})
    .refine((data) => data.password === data.confirmPassword, {
        message: "As senhas não coincidem",
        path: ["confirmPassword"],
    });
