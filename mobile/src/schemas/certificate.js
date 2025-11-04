import {z} from "zod";

export const certificateSchema = z.object({
    id: z.string({message: "ID do certificado é obrigatório"}).min(24, {message: "ID do certificado deve ter no mínimo 24 caracteres"})
});