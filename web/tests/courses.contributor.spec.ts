import { expect, test } from './fixtures'
import { authenticatePage } from './helpers/auth'
import {
  createCourseByApi,
  getCourseDetailsByApi,
  getCourseDetailsResponseByApi,
  searchCoursesByApi,
} from './helpers/course-api'
import { createCourseInput, createLessonInput } from './helpers/course-data'
import { CreateCoursePage } from './page-objects/CreateCoursePage'
import { CoursesCatalogPage } from './page-objects/CoursesCatalogPage'
import { EditCoursePage } from './page-objects/EditCoursePage'

test.describe('Courses Contributor', () => {
  test('should create a course with lessons', async ({ page, request, confirmedContributor }) => {
    const courseInput = createCourseInput()

    await authenticatePage(page, confirmedContributor.accessToken)

    const createCoursePage = new CreateCoursePage(page)
    await createCoursePage.goto()
    await createCoursePage.fillCourseDetails(courseInput)
    await createCoursePage.submit()

    await expect(page).toHaveURL(/\/categorias$/)

    const createdCourses = await searchCoursesByApi(request, confirmedContributor.accessToken, {
      contributorId: confirmedContributor.id,
      name: courseInput.name,
      matchType: 'PERFECT',
    })

    expect(createdCourses.some((course) => course.name.name === courseInput.name)).toBeTruthy()

    const catalogPage = new CoursesCatalogPage(page)
    await catalogPage.goto(courseInput.name)
    await catalogPage.expectCourseVisible(courseInput.name)
  })

  test('should edit an existing course and persist the new data after reload', async ({
    page,
    request,
    confirmedContributor,
  }) => {
    const originalCourse = await createCourseByApi(request, confirmedContributor, createCourseInput())
    const updatedCourse = {
      name: `Curso atualizado de teste ${Date.now()}`,
      category: 'DATA',
      level: 'advanced',
      description: `Descricao atualizada e validada apos reload ${Date.now()} com detalhes suficientes.`,
    }

    await authenticatePage(page, confirmedContributor.accessToken)

    const editCoursePage = new EditCoursePage(page)
    await editCoursePage.goto(originalCourse.course.id)
    await editCoursePage.updateCourseDetails(updatedCourse)

    await page.reload()
    await editCoursePage.expectCoursePlaceholders(updatedCourse)

    const refreshedCourse = await getCourseDetailsByApi(
      request,
      confirmedContributor.accessToken,
      originalCourse.course.id
    )

    expect(refreshedCourse.course.name.name).toBe(updatedCourse.name)
    expect(refreshedCourse.course.description).toBe(updatedCourse.description)
  })

  test('should add, edit and delete lessons in an existing course', async ({
    page,
    request,
    confirmedContributor,
  }) => {
    const course = await createCourseByApi(request, confirmedContributor, createCourseInput())
    const newLesson = createLessonInput('nova')
    const updatedLesson = createLessonInput('editada', {
      youtubeLink: createLessonInput('editada-link').youtubeLink,
    })

    await authenticatePage(page, confirmedContributor.accessToken)

    const editCoursePage = new EditCoursePage(page)
    await editCoursePage.goto(course.course.id)
    await editCoursePage.createLesson(newLesson)

    await expect
      .poll(async () => {
        const refreshedCourse = await getCourseDetailsByApi(
          request,
          confirmedContributor.accessToken,
          course.course.id
        )

        return refreshedCourse.lessons.some((lesson) => lesson.title === newLesson.title)
      })
      .toBeTruthy()

    let refreshedCourse = await getCourseDetailsByApi(
      request,
      confirmedContributor.accessToken,
      course.course.id
    )

    const createdLessonIndex = refreshedCourse.lessons.findIndex(
      (lesson) => lesson.title === newLesson.title
    )

    await page.reload()
    await editCoursePage.updateLesson(createdLessonIndex, updatedLesson)

    await expect
      .poll(async () => {
        const nextCourse = await getCourseDetailsByApi(
          request,
          confirmedContributor.accessToken,
          course.course.id
        )

        return nextCourse.lessons.some(
          (lesson) =>
            lesson.title === updatedLesson.title &&
            lesson.description === updatedLesson.description &&
            lesson.youtubeLink === updatedLesson.youtubeLink
        )
      })
      .toBeTruthy()

    refreshedCourse = await getCourseDetailsByApi(
      request,
      confirmedContributor.accessToken,
      course.course.id
    )

    const updatedLessonIndex = refreshedCourse.lessons.findIndex(
      (lesson) => lesson.title === updatedLesson.title
    )

    await page.reload()
    await editCoursePage.deleteLesson(updatedLessonIndex)

    await expect
      .poll(async () => {
        const nextCourse = await getCourseDetailsByApi(
          request,
          confirmedContributor.accessToken,
          course.course.id
        )

        return nextCourse.lessons.some((lesson) => lesson.title === updatedLesson.title)
      })
      .toBeFalsy()
  })

  test('should deactivate an existing course', async ({ page, request, confirmedContributor }) => {
    const course = await createCourseByApi(request, confirmedContributor, createCourseInput())

    await authenticatePage(page, confirmedContributor.accessToken)

    const editCoursePage = new EditCoursePage(page)
    await editCoursePage.goto(course.course.id)
    await editCoursePage.openDeleteCourseModal()
    await editCoursePage.confirmCourseDeletion()

    await expect(page).toHaveURL(/\/user\/cursos$/)

    const response = await getCourseDetailsResponseByApi(
      request,
      confirmedContributor.accessToken,
      course.course.id
    )

    expect(response.ok()).toBeFalsy()
    expect(response.status()).toBeGreaterThanOrEqual(400)
  })
})
