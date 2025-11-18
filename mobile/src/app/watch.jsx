import React, { useState, useEffect, useMemo, useRef } from "react";
import { View, ScrollView, KeyboardAvoidingView, Platform, Alert } from "react-native";
import { useLocalSearchParams } from "expo-router";
import PlayerHeader from "../components/watch/playerHeader";
import WatchHeader from "../components/watch/watchHeader";
import WatchTabs from "../components/watch/watchTabs";
import LessonTabContent from "../components/watch/lessonTabContent";
import CommentsTabContent from "../components/watch/commentsTabContent";
import CommentsPreview from "../components/watch/commentPreview";
import { useLessons } from "../hooks/useLessons";
import { useLessonComments } from "../hooks/useLessonComments";
import { useProgressByCourseId } from "../hooks/useProgressByCourseId";
import { markLessonAsWatchedRequest } from "../requests/courses/markLessonAsWatchedRequest";
import { downloadCertificate } from "../utils/downloadCertificate";
import SkeletonPlayerHeader from "../components/skeletonScreens/skeletonPlayerHeader";

export default function Watch() {
  const scrollRef = useRef(null);
  const params = useLocalSearchParams();
  const { courseID, courseSlug } = params;

  const { lessons, loading, error, refetch } = useLessons({ courseNameSlug: courseSlug });
  const { progress, refetch: refetchProgress } = useProgressByCourseId(courseID);

  const [tab, setTab] = useState("lessons");
  const [currentLesson, setCurrentLesson] = useState(null);
  const [watched, setWatched] = useState(new Set());
  const [isCertificateLoading, setIsCertificateLoading] = useState(false);
  const [commentInputY, setCommentInputY] = useState(0);
  const [commentsMap, setCommentsMap] = useState({});

  const { comments, refetch: refetchComments } = useLessonComments(currentLesson ? { lessonId: currentLesson.id } : {});

  useEffect(() => {
    if (lessons.length > 0 && !currentLesson) {
      setCurrentLesson(lessons[0]);
    }
  }, [lessons]);

  useEffect(() => {
    if (progress?.lessonsWatched?.length) {
      setWatched(new Set(progress.lessonsWatched));
    }
  }, [progress]);

  useEffect(() => {
    if (!comments || !currentLesson) return;

    setCommentsMap((prev) => ({
      ...prev,
      [currentLesson.id]: comments,
    }));
  }, [comments, currentLesson]);

  const handleToggleWatched = async (lessonId) => {
    try {
      if (progress?.lessonsWatched?.includes(lessonId)) return;

      await markLessonAsWatchedRequest(lessonId);
      setWatched((prev) => new Set(prev).add(lessonId));
      await refetchProgress();
    } catch (error) {
      Alert.alert("Erro", "Não foi possível marcar a aula como assistida. Tente novamente mais tarde.");
    }
  };

  const progressPercent = useMemo(() => {
    return lessons.length > 0 ? Math.round((watched.size / lessons.length) * 100) : 0;
  }, [watched.size, lessons.length]);

  const currentComments = commentsMap[currentLesson?.id] ?? [];
  const latest = currentComments[0] || null;
  const allWatched = lessons.length > 0 && watched.size === lessons.length;

  const scrollToComment = () => {
    requestAnimationFrame(() => {
      scrollRef.current?.scrollTo({
        y: Math.max(0, commentInputY - 80),
        animated: true,
      });
    });
  };
  return (
    <KeyboardAvoidingView
      className="flex-1 bg-custom-primary"
      behavior={Platform.OS === "android" ? "padding" : "position"}
      keyboardVerticalOffset={Platform.OS === "android" ? 80 : -20}
    >
      <View className="flex-1 bg-custom-primary">
        <WatchHeader />

        {loading || !currentLesson ? (
          <SkeletonPlayerHeader />
        ) : (
          <PlayerHeader
            youtubeId={currentLesson.youtubeLink}
            title={currentLesson.title}
            description={currentLesson.description}
          />
        )}
        <ScrollView ref={scrollRef} className="flex-1 px-4" keyboardShouldPersistTaps="handled" showsVerticalScrollIndicator={false}>

          <WatchTabs
            tab={tab}
            setTab={setTab}
            commentsCount={currentComments.length}
          />

          <CommentsPreview
            latest={latest}
            count={currentComments.length}
            onPress={() => setTab("comments")}
          />

          {tab === "lessons" ? (
            <LessonTabContent
              lessons={lessons}
              isloading={loading}
              currentLesson={currentLesson}
              watched={watched}
              watchedCount={watched.size}
              progressPercent={progressPercent}
              onSelectLesson={(lesson) => setCurrentLesson(lesson)}
              onToggleWatched={handleToggleWatched}
              allWatched={allWatched}
              isCertificateLoading={isCertificateLoading}
              onCertificatePress={() =>
                downloadCertificate(courseID, setIsCertificateLoading)
              }
              disabledLessons={new Set(progress?.lessonsWatched || [])}
            />
          ) : (
            <CommentsTabContent
              currentLesson={currentLesson}
              currentComments={currentComments}
              commentInputY={commentInputY}
              setCommentInputY={setCommentInputY}
              scrollToComment={scrollToComment}
              refetchComments={refetchComments}
            />
          )}
        </ScrollView>
      </View>
    </KeyboardAvoidingView>
  );
}
