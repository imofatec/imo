import { z } from "zod";

export const editUserSchema = z.object({
    user: z
        .string({ message: "Nome obrigatório" })
        .min(5, { message: "Nome deve ter no mínimo 5 caracteres" })
        .optional(),

    email: z
        .string({ message: "Email obrigatório" })
        .email({ message: "Email inválido" })
        .optional(),

    password: z
        .string({ message: "Senha obrigatória" })
        .min(6, { message: "Senha deve ter no mínimo 6 caracteres" })
        .optional(),

    confirmPassword: z.string().optional(),
})
    .superRefine((data, ctx) => {
        if (data.password && !data.confirmPassword) {
            ctx.addIssue({
                path: ["confirmPassword"],
                message: "Confirme a nova senha",
            });
        }

        if (data.password && data.confirmPassword && data.password !== data.confirmPassword) {
            ctx.addIssue({
                path: ["confirmPassword"],
                message: "As senhas não coincidem",
            });
        }
    });