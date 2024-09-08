import { Titulo } from "@/components/ui/titulo";
import Featurecourses from "@/components/featuredcourses";

export default function Index() {

    const courses = [
        { title: "Front End" },
        { title: "Back End", order: ["text", "list", "image"] },
        { title: "Tigrinho" }
    ]
    return (
        <>
            <Titulo titulo={"IMO"} />

            <div className="min-h-screen px-4 md:px-8 flex flex-col">

                {courses.map((item, index) => {
                    return (
                        <>
                            <Featurecourses key={index} title={item.title} text={item.text} order={item.order} />
                            <div className="h-[1px] bg-white w-full md:w-[61.75rem] m-auto my-4"></div>
                        </>
                    )
                })}
            </div>
        </>
    );
}
