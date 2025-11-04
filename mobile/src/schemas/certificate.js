import {z} from "zod";

export const certificateSchema = z.object({
    id: z.string({message: "ID do certificado é obrigatório"}),
});