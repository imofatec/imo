import {
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/dropdown/accordion"
import { Seletor } from "./seletor"

function Seletores({ contItem }) {
  const lista = []
  const conteudo = "Lorem Ipsum"
  for (let i = 0; i < contItem; i++) {
    lista.push(<Seletor key={i} conteudo={conteudo}></Seletor>)
  }
  return <>{lista}</>
}

export default function ItemDropdown({ numItem, labelItem, contItem, conteudo }) {

  return <>
    <AccordionItem value={numItem}>
      <AccordionTrigger>{labelItem}</AccordionTrigger>
      <AccordionContent>
        <Seletores contItem={contItem} conteudo={conteudo}></Seletores>
      </AccordionContent>
    </AccordionItem>
  </>
}