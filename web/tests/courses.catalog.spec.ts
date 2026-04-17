import { test } from './fixtures'
import { authenticatePage } from './helpers/auth'
import { createCourseByApi } from './helpers/course-api'
import { createCourseInput } from './helpers/course-data'
import { CoursesCatalogPage } from './page-objects/CoursesCatalogPage'

test.describe('Courses Catalog', () => {
  test('should filter courses by category and open the details modal', async ({
    page,
    request,
    authenticatedUser,
    confirmedContributor,
  }) => {
    const searchPrefix = `catalog-${Date.now()}`
    const aiCourse = await createCourseByApi(
      request,
      confirmedContributor,
      createCourseInput({
        name: `${searchPrefix} curso IA completo`,
        category: 'AI',
      })
    )
    const dataCourse = await createCourseByApi(
      request,
      confirmedContributor,
      createCourseInput({
        name: `${searchPrefix} curso dados completo`,
        category: 'DATA',
      })
    )

    await authenticatePage(page, authenticatedUser.accessToken)

    const catalogPage = new CoursesCatalogPage(page)
    await catalogPage.goto(searchPrefix)
    await catalogPage.filterByCategory(aiCourse.course.category.name)

    await catalogPage.expectCourseVisible(aiCourse.course.name.name)
    await catalogPage.expectCourseHidden(dataCourse.course.name.name)

    await catalogPage.openCourseModal(aiCourse.course.name.name)
    await catalogPage.expectModalToShowCourse(aiCourse.course)
  })
})
