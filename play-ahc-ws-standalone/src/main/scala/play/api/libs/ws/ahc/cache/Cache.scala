/*
 * Copyright (C) from 2022 The Play Framework Contributors <https://github.com/playframework>, 2011-2021 Lightbend Inc. <https://www.lightbend.com>
 */

package play.api.libs.ws.ahc.cache

import scala.concurrent.Future

/**
 * A very simple cache trait.
 *
 * Implementations can write adapters that map through to this trait, i.e.
 *
 * {{{
 * import java.util.concurrent.TimeUnit
 * import scala.concurrent.Future
 *
 * import com.github.benmanes.caffeine.cache.{ Caffeine, Ticker }
 *
 * import play.api.libs.ws.ahc.cache.{
 *   Cache, EffectiveURIKey, ResponseEntry
 * }
 *
 * class CaffeineHttpCache extends Cache {
 *   val underlying = Caffeine.newBuilder()
 *     .ticker(Ticker.systemTicker())
 *     .expireAfterWrite(365, TimeUnit.DAYS)
 *     .build[EffectiveURIKey, ResponseEntry]()
 *
 *   def remove(key: EffectiveURIKey) =
 *     Future.successful(Option(underlying.invalidate(key)))
 *
 *   def put(key: EffectiveURIKey, entry: ResponseEntry) =
 *     Future.successful(underlying.put(key, entry))
 *
 *   def get(key: EffectiveURIKey) =
 *     Future.successful(Option(underlying getIfPresent key ))
 *
 *   def close(): Unit = underlying.cleanUp()
 * }
 * }}}
 */
trait Cache {

  def get(key: EffectiveURIKey): Future[Option[ResponseEntry]]

  def put(key: EffectiveURIKey, entry: ResponseEntry): Future[Unit]

  def remove(key: EffectiveURIKey): Future[Unit]

  def close(): Unit

}
