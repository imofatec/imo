import {
  AccordionItem,
} from "@/components/ui/dropdown/accordion"
import { Seletor } from "./seletor"


export default function ItemDropdown({ labelItem , sizeLabel}) {

  return <>
    <AccordionItem>
    <Seletor sizeLabel={sizeLabel}conteudo={ labelItem }></Seletor>
    </AccordionItem>
  </>
}