import { expect, test } from './fixtures'
import { authenticatePage } from './helpers/auth'
import { createCourseByApi, markLessonAsWatchedByApi } from './helpers/course-api'
import { createCourseInput, createLessonInput } from './helpers/course-data'
import { WatchCoursePage } from './page-objects/WatchCoursePage'

test.describe('Courses Learning', () => {
  test('should watch a lesson, mark it as watched and update the course progress', async ({
    page,
    request,
    authenticatedUser,
    confirmedContributor,
  }) => {
    const progressLessons = [createLessonInput('progresso-1'), createLessonInput('progresso-2')]
    const course = await createCourseByApi(
      request,
      confirmedContributor,
      createCourseInput({ lessons: progressLessons })
    )

    await authenticatePage(page, authenticatedUser.accessToken)

    const watchCoursePage = new WatchCoursePage(page)
    await watchCoursePage.goto(course.course.id, course.lessons[0].youtubeLink)
    await watchCoursePage.expectCurrentLesson(course.lessons[0].title)
    await watchCoursePage.expectWatchedCounter(0, course.lessons.length)

    await watchCoursePage.markLessonAsWatched(course.lessons[0].indexInCourse)

    await watchCoursePage.expectWatchedCounter(1, course.lessons.length)
    await watchCoursePage.expectProgressWidth(50)
    await expect(page.getByText('Assistido', { exact: true }).first()).toBeVisible()
  })

  test('should add and display comments on a lesson', async ({
    page,
    request,
    authenticatedUser,
    confirmedContributor,
  }) => {
    const course = await createCourseByApi(
      request,
      confirmedContributor,
      createCourseInput({ lessons: [createLessonInput('comentario')] })
    )
    const comment = `Comentario de teste ${Date.now()}`

    await authenticatePage(page, authenticatedUser.accessToken)

    const watchCoursePage = new WatchCoursePage(page)
    await watchCoursePage.goto(course.course.id, course.lessons[0].youtubeLink)
    await watchCoursePage.submitComment(comment)
    await watchCoursePage.expectCommentVisible(comment)
  })

  test('should issue a certificate when the course is complete', async ({
    page,
    request,
    authenticatedUser,
    confirmedContributor,
  }) => {
    const course = await createCourseByApi(request, confirmedContributor, createCourseInput())

    for (const lesson of course.lessons) {
      await markLessonAsWatchedByApi(request, authenticatedUser.accessToken, lesson.id)
    }

    await authenticatePage(page, authenticatedUser.accessToken)

    const watchCoursePage = new WatchCoursePage(page)
    await watchCoursePage.goto(course.course.id, course.lessons[0].youtubeLink)

    await expect(watchCoursePage.certificateButton()).toBeEnabled()

    const [response, download] = await Promise.all([
      page.waitForResponse((requestResponse) =>
        requestResponse.url().includes(`/api/certificate/issue/${course.course.id}`)
      ),
      page.waitForEvent('download'),
      watchCoursePage.certificateButton().click(),
    ])

    expect(response.ok()).toBeTruthy()
    expect(download.suggestedFilename()).toContain('.pdf')
  })
})
