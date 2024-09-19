import { Titulo } from "@/components/ui/titulo";
import { InputLabel } from "@/components/ui/inputlabel";
import { Button } from "@/components/ui/button";
import { Form } from "react-router-dom";


export default function CreateCourses() {
   

    return (
        <div className="text-white">
            <Titulo titulo={"Criar Curso"} />
            <h1>Página de criação de curso</h1>
            <p>Aqui você pode adicionar cursos.</p>
            
            <div className="flex justify-center items-center">
                <Form method="post" action="/criarcurso">
                    <InputLabel type="text" id="name" name="name" placeholder="name" label="name" />
                    <InputLabel type="text" id="category" name="category" placeholder="category" label="category" />
                    <InputLabel type="text" id="description" name="description" placeholder="description" label="description" />
                    <h2>aulas curso</h2>
                    <InputLabel type="text" id="title" name="title" placeholder="title" label="title" />
                    <InputLabel type="text" id="descriptionC" name="descriptionC" placeholder="descriptionC" label="descriptionC" />
                    <InputLabel type="text" id="youtubeLink" name="youtubeLink" placeholder="youtubeLink" label="youtubeLink" />
                    <Button type="submit" className="w-full bg-custom-light-blue">Cadastrar-se</Button>
                </Form>
            </div>
        </div>
    );
}