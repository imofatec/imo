import { Dropdown } from '@/components/ui/dropdown/dropdown'
import { Seletor } from '@/components/ui/dropdown/seletor'
export default function CategorySelector({
  selectedCategory,
  onCategorySelect,
  onShowAllCourses,
  onShowContributions,
  viewMode,
}) {
  const categories = [
    { name: 'Finalizados', slug: 'finalizados' },
    { name: 'Em andamento', slug: 'em-andamento' },
  ]
  return (
    <>
      <Seletor
        id="allLessons"
        label={'text-xl font-semibold'}
        conteudo={'Todos os cursos'}
        onShowAllCourses={onShowAllCourses}
        isSelected={viewMode === 'courses' && !selectedCategory}
      ></Seletor>
      <div className='w-full h-[1px] bg-white'></div>
      <Seletor
        id="allContributions"
        label={'text-xl font-semibold'}
        conteudo={'Contribuições'}
        onShowContributions={onShowContributions}
        isSelected={viewMode === 'contributions' && !selectedCategory}
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
