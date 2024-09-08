import { Accordion } from "@/components/ui/dropdown/accordion"
import ItemDropdown from "./itemdropdown"
import { Seletor } from "./seletor"

export function Dropdown() {

  const nomeCursos = [
    { title: "Data Science" },
    { title: "Redes" },
    { title: "Gestão" },
    { title: "Design" },
    { title: "Programação" },
    { title: "Classificação" }

  ]

  return (

    <Accordion type="single" collapsible className="text-center ">
      {nomeCursos.map((dado, i) => {
        return (
          <div>
            <ItemDropdown key={i} numItem={i} labelItem={dado.title} contItem={<Seletor></Seletor>}></ItemDropdown>
          </div>
        )

      })}
    </Accordion>
  )
}
