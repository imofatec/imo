import { Dropdown } from '@/components/ui/dropdown/dropdown'
import { Seletor } from '@/components/ui/dropdown/seletor'
export default function CategorySelector({
  selectedCategory,
  onCategorySelect,
  onShowAllCourses,
}) {
  let categories = [
    { name: 'Finalizados', slug: 'finalizados' },
    { name: 'Submissões', slug: 'submissoes' },
    { name: 'Em andamento', slug: 'em-andamento' },
  ]
  return (
    <>
      <Seletor
        id="allLessons"
        label={'text-xl font-semibold'}
        conteudo={'Todos os cursos'}
        onShowAllCourses={onShowAllCourses}
        isSelected={!selectedCategory}
      ></Seletor>
      <Dropdown
        categorias={categories}
        onCategorySelect={onCategorySelect}
        selectedCategory={selectedCategory}
        url="user/cursos"
      ></Dropdown>
    </>
  )
}
