import { Titulo } from "@/components/ui/titulo";
import Featurecourses from "@/components/featuredcourses";

export default function Index() {

    const courses = [
        { title: "Data Science" },
        { title: "Redes", order: ["text", "list", "image"] },
        { title: "Gestão" }
    ]
    return (
        <>
            <Titulo titulo={"IMO"} />

            <div className="bg-custom-dark-purple min-h-screen px-4 md:px-8 flex flex-col">

                {courses.map((item, index, i) => {
                    return (
                        <>
                            <Featurecourses key={index} title={item.title} text={item.text} order={item.order} />
                            <div className="h-[1px] bg-white w-full max-w-[900px] m-auto my-4"></div>
                        </>
                    )
                })}
            </div>
        </>
    );
}
